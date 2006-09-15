package com.aetrion.activerecord.annotations.associations;

import com.aetrion.activerecord.Dependency;

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
public @interface HasAndBelongsToMany {
    String foreignKey() default "";
    String associationForeignKey() default "";
    String joinTable() default "";

    Dependency dependent() default Dependency.DESTROY;

    Class[] include() default {};

    String order() default "id";

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
    String deleteSql() default "";

    Class[] extend() default {};
}
