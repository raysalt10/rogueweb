package com.aetrion.actioncontroller.error;

/**
 * Base exception for all action controller errors.
 *
 * @author Anthony Eden
 */
public class ActionControllerException extends RuntimeException {

    public ActionControllerException() {

    }

    public ActionControllerException(String message) {
        super(message);
    }

    public ActionControllerException(Throwable cause) {
        super(cause);
    }

    public ActionControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
