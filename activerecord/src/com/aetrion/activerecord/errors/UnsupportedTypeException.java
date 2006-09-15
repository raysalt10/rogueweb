package com.aetrion.activerecord.errors;

/**
 * Exception which is thrown when an unsupported SQL type is used.
 *
 * @author Anthony Eden
 */
public class UnsupportedTypeException extends ActiveRecordException {
    public UnsupportedTypeException(String message) {
        super(message);
    }
}
