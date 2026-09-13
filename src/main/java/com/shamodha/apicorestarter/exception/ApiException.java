package com.shamodha.apicorestarter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 6:13 PM
 * Project : core-infrastructure
 * ========================================================
 */

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}