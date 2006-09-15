package com.aetrion.activerecord.annotations.aggregations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * 
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface ComposedOf {
    String[] mapping() default {};
    boolean allowNull() default false;
}
