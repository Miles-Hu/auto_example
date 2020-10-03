package com.github.z.auto.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hujun
 * date 2019-08-16 11:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoExample {

  Class<?> value() default AutoExample.class ;

  String classFullName() default "";

  boolean useSecondaryCache() default false;

  String[] orderBy() default "";

  boolean distinct() default false;
}
