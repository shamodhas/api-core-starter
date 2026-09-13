package com.shamodha.apicorestarter.security.service;

import com.shamodha.apicorestarter.config.CoreSecurityProperties;
import com.shamodha.apicorestarter.security.converter.ClaimsConverter;
import com.shamodha.apicorestarter.security.model.TokenPair;
import com.shamodha.apicorestarter.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 6:38 PM
 * Project : core-infrastructure
 * ========================================================
 */

@Service
@RequiredArgsConstructor
public class JwtService {

    private final CoreSecurityProperties securityProperties;
    private final ClaimsConverter claimsConverter;

    @SafeVarargs
    public final String generateToken(UserContext userContext, Map<String, Object>... extraClaims) {
        Map<String, Object> claims = new HashMap<>(claimsConverter.toClaims(userContext));

        if (extraClaims != null && extraClaims.length > 0 && extraClaims[0] != null) {
            claims.putAll(extraClaims[0]);
        }

        return buildToken(claims, userContext.getUserId(), securityProperties.jwt().expiration(), getSignInKey());
    }

    public final String generateRefreshToken(UserContext userContext) {
        Long refreshExpiration = securityProperties.jwt().refreshExpiration();
        if (refreshExpiration == null || refreshExpiration <= 0) {
            return null;
        }

        return buildToken(Map.of(), userContext.getUserId(), refreshExpiration, getRefreshSignInKey());
    }

    @SafeVarargs
    public final TokenPair generateTokenPair(UserContext userContext, Map<String, Object>... extraClaims) {
        return new TokenPair(generateToken(userContext, extraClaims), generateRefreshToken(userContext));
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration, SecretKey key) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .signWith(key)
                .compact();
    }

    public UserContext extractAndValidateContext(String token, boolean isRefresh) {
        Claims claims = Jwts.parser()
                .verifyWith(isRefresh ? getRefreshSignInKey() : getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsConverter.convertToUserContext(claims);
    }

    private SecretKey getSignInKey() {
        return decodeKey(securityProperties.jwt().secret());
    }

    private SecretKey getRefreshSignInKey() {
        String refreshSecret = securityProperties.jwt().refreshSecret();
        String secret = (refreshSecret != null && !refreshSecret.isBlank())
                ? refreshSecret
                : securityProperties.jwt().secret();
        return decodeKey(secret);
    }

    private SecretKey decodeKey(String secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}