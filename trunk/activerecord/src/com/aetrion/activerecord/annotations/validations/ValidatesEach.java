package com.aetrion.activerecord.annotations.validations;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * 
 */
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface ValidatesEach {
    String[] fields() default {};
    String on() default "save";
    boolean allowNull() default false;
    String doIf() default "";

}
