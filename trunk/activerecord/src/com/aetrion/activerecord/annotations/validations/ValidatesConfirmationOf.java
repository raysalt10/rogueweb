package com.aetrion.activerecord.annotations.validations;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 
 */
@Documented
@Target({FIELD, TYPE})
@Retention(RUNTIME)
public @interface ValidatesConfirmationOf {
    /**
     * Fields which should be validated. Not needed if annotation is used with a field.
     */
    String[] value() default {};

    /**
     * The error message.
     */
    String message() default "";

    /**
     * When to execute the validations.
     */
    String[] on() default {"save"};

    /**
     * Do the validation if the specified method returns true.
     */
    String doIf() default "";
}
