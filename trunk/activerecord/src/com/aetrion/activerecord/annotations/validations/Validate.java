package com.aetrion.activerecord.annotations.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 
 */
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface Validate {
    /**
     * List of method names which should be executed with validations.
     */
    String[] value() default {};
}
