package com.aetrion.activerecord.errors;

/**
 * Base exception for all ActiveRecord errors.
 *
 * @author Anthony Eden
 */
public class ActiveRecordException extends RuntimeException {
    public ActiveRecordException() {
        super();
    }
    public ActiveRecordException(String message) {
        super(message);
    }
    public ActiveRecordException(Throwable cause) {
        super(cause);
    }
    public ActiveRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
