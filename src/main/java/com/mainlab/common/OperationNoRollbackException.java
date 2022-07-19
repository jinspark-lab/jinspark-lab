package com.mainlab.common;


public abstract class OperationNoRollbackException extends RuntimeException{
    private static final long serialVersionUID = 3861635326201397077L;

    public OperationNoRollbackException() {
        super();
    }

    public OperationNoRollbackException(String message) {
        super(message);
    }

    public OperationNoRollbackException(Throwable cause) {
        super(cause);
    }

    public OperationNoRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNoRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
