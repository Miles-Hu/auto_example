package com.vipkid.auto.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hujun1
 * @date 2019-08-16 11:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoExample {

  Class<?> value();

  boolean useSecondaryCache() default false;

  String[] orderBy() default "";

  boolean distinct() default false;
}
