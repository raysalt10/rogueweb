package com.aetrion.activerecord.annotations.associations;

import com.aetrion.activerecord.Dependency;


import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * 
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface HasMany {
    String foreignKey() default "";

    Dependency dependent() default Dependency.NONE;

    Class[] include() default {};
    Class[] through() default {};
    Class[] source() default {};

    String order() default "id";

    String as() default "";

    int limit() default -1;

    String select() default "*";
    String[] conditions() default {};

    boolean unique() default false;

    String[] beforeAdd() default {};
    String[] afterAdd() default {};
    String[] beforeRemove() default {};
    String[] afterRemove() default {};

    String finderSql() default "";
    String counterSql() default "";

    Class[] extend() default {};
}
