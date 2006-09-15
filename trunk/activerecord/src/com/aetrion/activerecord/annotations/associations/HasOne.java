package com.aetrion.activerecord.annotations.associations;

import com.aetrion.activerecord.Dependency;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Annotation representing a "has one" association.
 *
 * @author Anthony Eden
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface HasOne {
    String foreignKey() default "";

    Dependency dependent() default Dependency.NONE;

    Class[] include() default {};

    String as() default "";
}
