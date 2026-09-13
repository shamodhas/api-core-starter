package com.shamodha.apicorestarter.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements BaseApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;

    @Builder.Default
    private long timestamp = System.currentTimeMillis();
}