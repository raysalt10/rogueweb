package com.aetrion.activerecord.errors;

/**
 * Exception which is thrown when a specified option cannot be set.
 *
 * @author Anthony Eden
 */
public class InvalidOptionException extends ActiveRecordException {
    public InvalidOptionException() {

    }
    public InvalidOptionException(String message) {
        super(message);
    }
}
