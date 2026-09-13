package com.shamodha.apicorestarter.config;

import com.shamodha.apicorestarter.security.resolver.CurrentUserArgumentResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 1:43 AM
 * Project : core-infrastructure
 * ========================================================
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ObjectProvider<CurrentUserArgumentResolver> currentUserArgumentResolverProvider;

    public WebMvcConfig(ObjectProvider<CurrentUserArgumentResolver> currentUserArgumentResolverProvider) {
        this.currentUserArgumentResolverProvider = currentUserArgumentResolverProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public CurrentUserArgumentResolver currentUserArgumentResolver(
            ObjectProvider<UserDetailsService> userDetailsServiceProvider
    ) {
        return new CurrentUserArgumentResolver(userDetailsServiceProvider.getIfAvailable());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        CurrentUserArgumentResolver resolver = currentUserArgumentResolverProvider.getIfAvailable();
        if (resolver != null) {
            resolvers.add(0, resolver);
        }
    }
}