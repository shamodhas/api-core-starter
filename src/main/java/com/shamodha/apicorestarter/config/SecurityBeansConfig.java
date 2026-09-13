package com.shamodha.apicorestarter.config;

import com.shamodha.apicorestarter.security.converter.ClaimsConverter;
import com.shamodha.apicorestarter.security.filter.JwtAuthenticationFilter;
import com.shamodha.apicorestarter.security.handler.CustomAccessDeniedHandler;
import com.shamodha.apicorestarter.security.handler.CustomAuthenticationEntryPoint;
import com.shamodha.apicorestarter.security.registry.PublicApiRegistry;
import com.shamodha.apicorestarter.security.service.JwtService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/12/2026 6:38 PM
 * Project : core-infrastructure
 * ========================================================
 */

@Configuration
@ConditionalOnProperty(
        prefix = "auth.security",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class SecurityBeansConfig {

    @Bean
    @ConditionalOnMissingBean
    public JwtService jwtService(CoreSecurityProperties securityProperties, ClaimsConverter claimsConverter) {
        return new JwtService(securityProperties, claimsConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    public ClaimsConverter claimsConverter() {
        return new ClaimsConverter.Default();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandler accessDeniedHandler(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        return new CustomAccessDeniedHandler(resolver);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        return new CustomAuthenticationEntryPoint(resolver);
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationProvider authenticationProvider(
            ObjectProvider<UserDetailsService> userDetailsServiceProvider,
            PasswordEncoder passwordEncoder
    ) {
        UserDetailsService userDetailsService = userDetailsServiceProvider.getIfAvailable();
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(
                userDetailsService != null ? userDetailsService : identifier -> {
                    throw new org.springframework.security.core.userdetails.UsernameNotFoundException("UserDetailsService not configured");
                }
        );
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public PublicApiRegistry publicApiRegistry(RequestMappingHandlerMapping handlerMapping) {
        return new PublicApiRegistry(handlerMapping);
    }
}