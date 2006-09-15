package com.aetrion.activerecord.association;

import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.ActiveRecords;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Proxy invocation handler for the HasOne association. This proxy will lazy-load the associated object.
 *
 * @author Anthony Eden
 */
public class HasOneProxy extends AssociationProxy implements InvocationHandler {

    private Field field;
    private HasOne annotation;
    private Object associated;

    public HasOneProxy(Field field, HasOne annotation) {
        this.field = field;
        this.annotation = annotation;
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        if (associated == null) associated = loadObject();
        return method.invoke(associated, objects);
    }

    Object loadObject() {
        Class c = field.getType();
        AROptions options = new AROptions();
        if (!annotation.foreignKey().equals("")) options.primaryKey = annotation.foreignKey();
//        if (annotation.conditions().length > 0) options.conditions = annotation.conditions();
//        if (annotation.select().length() > 0) options.select = annotation.select();
        if (annotation.include().length > 0) options.joins = ""; // TODO implement
        return ActiveRecords.findFirst(c, options);
    }
}
