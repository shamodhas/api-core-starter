package com.shamodha.apicorestarter.config.properties;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 9:14 PM
 * Project : core-infrastructure
 * ========================================================
 */

public record CorsProperties(
        @NotEmpty List<String> allowedOrigins,
        @NotEmpty List<String> allowedMethods,
        @NotEmpty List<String> allowedHeaders
) {
}