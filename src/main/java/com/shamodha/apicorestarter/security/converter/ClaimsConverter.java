package com.shamodha.apicorestarter.security.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shamodha.apicorestarter.security.model.UserContext;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 9:58 AM
 * Project : core-infrastructure
 * ========================================================
 */

public interface ClaimsConverter {

    <T> T toObject(Claims claims, Class<T> targetClass);

    Map<String, Object> toClaims(Object source);

    UserContext convertToUserContext(Claims claims);

    class Default implements ClaimsConverter {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @Override
        public <T> T toObject(Claims claims, Class<T> targetClass) {
            return OBJECT_MAPPER.convertValue(claims, targetClass);
        }

        @Override
        public Map<String, Object> toClaims(Object source) {
            return OBJECT_MAPPER.convertValue(source, new TypeReference<>() {
            });
        }

        @Override
        public UserContext convertToUserContext(Claims claims) {
            return toObject(claims, UserContext.class);
        }
    }
}