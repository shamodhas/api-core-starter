package com.shamodha.apicorestarter.web.response;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 5:41 AM
 * Project : core-infrastructure
 * ========================================================
 */

public interface BaseApiResponse<T> {
    int getStatus();

    boolean isSuccess();

    String getMessage();

    T getData();
}