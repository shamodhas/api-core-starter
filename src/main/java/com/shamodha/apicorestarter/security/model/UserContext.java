package com.shamodha.apicorestarter.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

public interface UserContext extends UserDetails {
    String getUserId();

    String getUsernameOrEmail();

    List<String> getRoles();

    @Override
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

    @Override
    default String getPassword() {
        return "";
    }

    @Override
    default String getUsername() {
        return getUsernameOrEmail();
    }

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    default boolean isEnabled() {
        return true;
    }

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
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
