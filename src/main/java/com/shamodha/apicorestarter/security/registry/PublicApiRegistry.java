package com.shamodha.apicorestarter.security.registry;

import com.shamodha.apicorestarter.security.annotation.PublicApi;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 2:11 AM
 * Project : core-infrastructure
 * ========================================================
 */

public class PublicApiRegistry {

    private final RequestMappingHandlerMapping handlerMapping;
    private final List<String> publicPatterns = new ArrayList<>();
    private boolean scanned = false;

    public PublicApiRegistry(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public synchronized String[] getPublicPatterns() {
        if (!scanned) {
            handlerMapping.getHandlerMethods().forEach((mapping, handlerMethod) -> {
                if (handlerMethod.hasMethodAnnotation(PublicApi.class) ||
                        handlerMethod.getBeanType().isAnnotationPresent(PublicApi.class)) {

                    if (mapping.getPathPatternsCondition() != null) {
                        mapping.getPathPatternsCondition().getPatterns()
                                .forEach(pattern -> publicPatterns.add(pattern.getPatternString()));
                    }
                }
            });
            scanned = true;
        }
        return publicPatterns.toArray(new String[0]);
    }
}