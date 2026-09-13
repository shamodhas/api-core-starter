package com.shamodha.apicorestarter.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shamodha.apicorestarter.web.response.ApiResponse;
import com.shamodha.apicorestarter.web.response.BaseApiResponse;
import com.shamodha.apicorestarter.web.response.ResponseHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 12:07 AM
 * Project : core-infrastructure
 * ========================================================
 */

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        String packageName = returnType.getDeclaringClass().getPackageName();
        return !packageName.startsWith("org.springdoc") && !packageName.startsWith("org.springframework.boot.actuate");
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body instanceof BaseApiResponse) {
            return body;
        }

        Object rawBody = body;
        if (body instanceof ResponseEntity<?> responseEntity) {
            response.setStatusCode(responseEntity.getStatusCode());
            rawBody = responseEntity.getBody();
            if (rawBody instanceof BaseApiResponse) {
                return rawBody;
            }
        }

        @SuppressWarnings("unchecked")
        ApiResponse<Object> apiResponse = ResponseHandler.ok(rawBody).getBody();

        if (rawBody instanceof String) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return objectMapper.writeValueAsString(apiResponse);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize string response", e);
            }
        }

        return apiResponse;
    }
}