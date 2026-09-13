package com.shamodha.apicorestarter.security.model;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 7:53 AM
 * Project : core-infrastructure
 * ========================================================
 */

public interface UserContext {
    String getUserId();

    String getUsernameOrEmail();

    List<String> getRoles();

    default Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = getRoles();
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.startsWith("ROLE_") ? role : "ROLE_" + role))
                .toList();
    }

    @Builder
    record Default(
            String userId,
            String usernameOrEmail,
            List<String> roles
    ) implements UserContext {
        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public String getUsernameOrEmail() {
            return usernameOrEmail;
        }

        @Override
        public List<String> getRoles() {
            return roles;
        }
    }

    static Default.DefaultBuilder builder() {
        return Default.builder();
    }
}