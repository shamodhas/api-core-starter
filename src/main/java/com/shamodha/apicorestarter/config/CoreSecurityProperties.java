package com.shamodha.apicorestarter.config;

import com.shamodha.apicorestarter.config.properties.CorsProperties;
import com.shamodha.apicorestarter.config.properties.JwtProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 6:56 PM
 * Project : core-infrastructure
 * ========================================================
 */

@Validated
@ConfigurationProperties(prefix = "auth.security")
public record CoreSecurityProperties(
        @NotNull Boolean enabled,
        @NotEmpty List<String> whitelist,
        @NotNull @Valid JwtProperties jwt,
        @NotNull @Valid CorsProperties cors
) {
}