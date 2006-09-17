package com.aetrion.activerecord.callbacks;

import com.aetrion.activerecord.annotations.callbacks.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Enumaration of lifecycle callback types.
 *
 * @author Anthony Eden
 */
public enum CallbackType {

    AFTER_CREATE,
    AFTER_DESTROY,
    AFTER_FIND,
    AFTER_INITIALIZATION,
    AFTER_SAVE,
    AFTER_UPDATE,
    AFTER_VALIDATION,
    BEFORE_CREATE,
    BEFORE_DESTROY,
    BEFORE_SAVE,
    BEFORE_UPDATE,
    BEFORE_VALIDATION;


    /**
     * Maps annotation classes for callbacks to CallbackTypes.
     */
    public static Map<Class,CallbackType> callbackTypeMap = new HashMap<Class,CallbackType>();
    static {
        callbackTypeMap.put(AfterCreate.class, AFTER_CREATE);
        callbackTypeMap.put(AfterDestroy.class, AFTER_DESTROY);
        callbackTypeMap.put(AfterFind.class, AFTER_FIND);
        callbackTypeMap.put(AfterInitialization.class, AFTER_INITIALIZATION);
        callbackTypeMap.put(AfterSave.class, AFTER_SAVE);
        callbackTypeMap.put(AfterUpdate.class, AFTER_UPDATE);
        callbackTypeMap.put(AfterValidation.class, AFTER_VALIDATION);
        callbackTypeMap.put(BeforeCreate.class, BEFORE_CREATE);
        callbackTypeMap.put(BeforeDestroy.class, BEFORE_DESTROY);
        callbackTypeMap.put(BeforeSave.class, BEFORE_SAVE);
        callbackTypeMap.put(BeforeUpdate.class, BEFORE_UPDATE);
        callbackTypeMap.put(BeforeValidation.class, BEFORE_VALIDATION);
    }

}
