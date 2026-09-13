package com.shamodha.apicorestarter.security.annotation;

import java.lang.annotation.*;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 9/13/2026 1:41 AM
 * Project : core-infrastructure
 * ========================================================
 */

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentDbUser {
}