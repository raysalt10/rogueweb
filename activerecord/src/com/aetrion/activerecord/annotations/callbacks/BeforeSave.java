package com.aetrion.activerecord.annotations.callbacks;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * 
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface BeforeSave {
}
