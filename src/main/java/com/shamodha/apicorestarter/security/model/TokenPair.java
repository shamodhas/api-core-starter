package com.shamodha.apicorestarter.security.model;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 8:43 AM
 * Project : core-infrastructure
 * ========================================================
 */

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}