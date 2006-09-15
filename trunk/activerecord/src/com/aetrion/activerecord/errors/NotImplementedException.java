package com.aetrion.activerecord.errors;

/**
 * Exception indicating that a particular method is not implemented.
 *
 * @author Anthony Eden
 */
public class NotImplementedException extends ActiveRecordException {

    public NotImplementedException(String message) {
        super(message);
    }

}
