package com.shamodha.apicorestarter.config;

import com.shamodha.apicorestarter.advice.GlobalResponseAdvice;
import com.shamodha.apicorestarter.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 6:14 PM
 * Project : core-infrastructure
 * ========================================================
 */

@AutoConfiguration
@EnableConfigurationProperties(CoreSecurityProperties.class)
@Import({
        SecurityBeansConfig.class,
        SecurityConfig.class,
        WebMvcConfig.class,
        GlobalResponseAdvice.class,
        GlobalExceptionHandler.class
})
public class CoreInfrastructureAutoConfiguration {
}