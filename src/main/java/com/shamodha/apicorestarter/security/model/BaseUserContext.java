package com.shamodha.apicorestarter.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 7:58 AM
 * Project : core-infrastructure
 * ========================================================
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserContext implements UserContext {

    private String userId;
    private String usernameOrEmail;
    private List<String> roles;

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

    public <T extends Enum<T>> List<T> getRolesAs(Class<T> enumType) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> Enum.valueOf(enumType, role.toUpperCase()))
                .toList();
    }

    public <T extends Enum<T>> boolean hasRole(T role) {
        if (roles == null || roles.isEmpty() || role == null) {
            return false;
        }
        return roles.stream().anyMatch(r -> r.equalsIgnoreCase(role.name()));
    }

    @SafeVarargs
    public final <T extends Enum<T>> boolean hasAnyRole(T... rolesToCheck) {
        if (roles == null || roles.isEmpty() || rolesToCheck == null) {
            return false;
        }
        for (T role : rolesToCheck) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            return false;
        }
        return identifier.equalsIgnoreCase(this.usernameOrEmail);
    }
}