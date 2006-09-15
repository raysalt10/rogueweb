package com.aetrion.activerecord.errors;

/**
 * 
 */
public class ReadOnlyException extends ActiveRecordException {
    public ReadOnlyException(String message) {
        super(message);
    }
}
