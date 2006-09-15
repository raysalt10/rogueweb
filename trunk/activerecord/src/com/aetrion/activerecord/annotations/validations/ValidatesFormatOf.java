package com.aetrion.activerecord.annotations.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 
 */
@Documented
@Target({FIELD,TYPE})
@Retention(RUNTIME)
public @interface ValidatesFormatOf {
    /**
     * The fields which are validated. This field is not necessary if this annotation is used with a field.
     */
    String[] value() default {};

    /**
     * The error message to use upon failure.
     */
    String message() default "";

    /**
     * The regular expression to validate with.
     */
    String with() default "";

    /**
     * When this validation should be executed.
     */
    String[] on() default {"save"};

    /**
     * Method to execute that must return null if the valdation should occur.
     */
    String doIf() default "";
}
