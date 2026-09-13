package com.shamodha.apicorestarter.config.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 9:13 PM
 * Project : core-infrastructure
 * ========================================================
 */

public record JwtProperties(
        @NotBlank String secret,
        @NotNull Long expiration,
        String refreshSecret,
        Long refreshExpiration
) {
}