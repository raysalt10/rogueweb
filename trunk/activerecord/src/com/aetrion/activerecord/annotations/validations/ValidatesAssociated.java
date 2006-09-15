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
public @interface ValidatesAssociated {
    /**
     * When the validation should be executed.
     */
    String[] on() default {"save"};

    /**
     * Do the validation if the named method returns true.
     */
    String doIf() default "";
}
