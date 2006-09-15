package com.aetrion.activerecord.annotations.associations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * 
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface BelongsTo {
    String foreignKey() default "";
    String[] conditions() default {};

    Class[] include() default {};

    boolean counterCache() default false;
    String counterCacheName() default "";

    boolean polymorphic() default false;

    Class[] extend() default {};
}
