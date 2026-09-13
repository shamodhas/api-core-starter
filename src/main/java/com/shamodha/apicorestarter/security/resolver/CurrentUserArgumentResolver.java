package com.shamodha.apicorestarter.security.resolver;

import com.shamodha.apicorestarter.constant.AppConstants;
import com.shamodha.apicorestarter.security.model.UserContext;
import com.shamodha.apicorestarter.security.annotation.CurrentDbUser;
import com.shamodha.apicorestarter.security.annotation.CurrentUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 1:28 AM
 * Project : core-infrastructure
 * ========================================================
 */

@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserDetailsService userDetailsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                || parameter.hasParameterAnnotation(CurrentDbUser.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || AppConstants.ANONYMOUS_USER.equals(auth.getPrincipal())) {
            return null;
        }

        if (parameter.hasParameterAnnotation(CurrentDbUser.class) && userDetailsService != null) {
            String identifier;
            Object principal = auth.getPrincipal();

            if (principal instanceof UserContext userContext) {
                identifier = userContext.getUsernameOrEmail();
            } else {
                identifier = auth.getName();
            }

            UserDetails dbUser = userDetailsService.loadUserByUsername(identifier);

            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    dbUser,
                    auth.getCredentials(),
                    dbUser.getAuthorities()
            );
            newAuth.setDetails(auth.getDetails());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return dbUser;
        }

        return auth.getPrincipal();
    }
}