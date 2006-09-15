package com.aetrion.activerecord.annotations.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Encapsulates the pattern of wanting to validate the acceptance of a terms of service check box (or similar agreement).
 *
 * @author Anthony Eden
 */
@Documented
@Target({FIELD,TYPE})
@Retention(RUNTIME)
public @interface ValidatesAcceptanceOf {
    /**
     * List of fields to validate. Not needed if the annotation occurs on a field.
     */
    String[] value() default {};

    /**
     * The error message used upon failure.
     */
    String message() default "";

    /**
     * When to execute the validation
     */
    String[] on() default {"save"};

    /**
     * Do the validation if the named method returns true.
     */
    String doIf() default "";

    /**
     * String which represents acceptance.
     */
    String accept() default "";
}
