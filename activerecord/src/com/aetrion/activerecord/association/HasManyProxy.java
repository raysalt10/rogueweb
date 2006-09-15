package com.aetrion.activerecord.association;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.adapter.Conditions;
import com.aetrion.activerecord.annotations.associations.HasMany;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Proxy invocation handler for the HasMany association. This proxy will lazy-load the collection of associated
 * objects.
 * @author Anthony Eden
 */
public class HasManyProxy extends AssociationProxy implements InvocationHandler {

    private ActiveRecord record;
    private Field field;
    private HasMany annotation;
    private List associated;
    private HasManyMetaData metaData;

    public HasManyProxy(ActiveRecord record, Field field, HasMany annotation) {
        this.record = record;
        this.field = field;
        this.annotation = annotation;
        this.metaData = new HasManyMetaData(record, annotation);
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        if (associated == null) associated = loadCollection();
        // invoke the method on the actual collection and return the result
        return method.invoke(associated, objects);
    }

    List loadCollection() {
        ParameterizedType ptype = (ParameterizedType) field.getGenericType();
        Class c = (Class) ptype.getActualTypeArguments()[0];
        AROptions options = new AROptions();
        options.primaryKey = metaData.getAssociationForeignKey();
        if (annotation.conditions().length > 0) options.conditions.add(new Conditions(annotation.conditions()));
        if (annotation.select().length() > 0) options.select = annotation.select();
        if (annotation.include().length > 0) options.joins = ""; // TODO implement

        if (metaData.getThrough() != null) {
            System.out.println("Processing a has_many through relationship");
            System.out.println("Through class: " + metaData.getThrough().getName());

            metaData.addHasManyThroughOptions(options);
        } else {
            String foriegnKey = ActiveRecords.foriegnKey(field.getDeclaringClass());
            if (options.primaryKey != null) foriegnKey = options.primaryKey;
            options.addConditions(foriegnKey + " = ?", String.valueOf(record.getId()));
        }

        return ActiveRecords.findAll(c, options);
    }
}
