package com.aetrion.activerecord.annotations.callbacks;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface BeforeDestroy {
}
