package com.aetrion.activerecord.validations;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.errors.ActiveRecordException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Execute a method for validation.
 *
 * @author Anthony Eden
 */
public class ValidateWithMethod extends Validator {

    private ActiveRecord record;
    private Method method;

    public ValidateWithMethod(ActiveRecord record, Method method) {
        this.record = record;
        this.method = method;
    }

    public void validate() {
        try {
            method.setAccessible(true);
            method.invoke(record);
        } catch (IllegalAccessException e) {
            throw new ActiveRecordException(e);
        } catch (InvocationTargetException e) {
            throw new ActiveRecordException(e);
        } finally {
            method.setAccessible(false);
        }
    }

}
