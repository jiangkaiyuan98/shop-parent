package com.shop.annotation;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 允许访问的次数，默认值20
     */
    int count() default 20;

    /**
     * 时间段，单位为毫秒，默认值一分钟
     */
    long time() default 60000;
}
