package com.aetrion.activerecord.errors;

/**
 * 
 */
public class StatementInvalidException extends ActiveRecordException {
    public StatementInvalidException(String message) {
        super(message);
    }
    public StatementInvalidException(Throwable cause) {
        super(cause);
    }
}
