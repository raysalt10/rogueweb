package com.aetrion.activerecord.annotations;

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
public @interface Table {
    String value();
}
