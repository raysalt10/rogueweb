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
@Target({FIELD, TYPE})
@Retention(RUNTIME)
public @interface ValidatesLengthOf {
    double minimum() default Double.MIN_VALUE;
    double maximum() default Double.MAX_VALUE;
    double is() default 0;
    double[] within();
    boolean allowNull() default false;

    String toLong() default "";
    String toShort() default "";
    String wrongLength() default "";
    String message() default "";
    
    String[] on() default {"save"};
    String doIf() default "";


}
