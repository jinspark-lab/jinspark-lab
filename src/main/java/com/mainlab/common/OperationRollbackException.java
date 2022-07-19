package com.mainlab.common;

public class OperationRollbackException extends RuntimeException {
    private static final long serialVersionUID = -2838497478737816974L;

    public OperationRollbackException() {
        super();
    }

    public OperationRollbackException(String message) {
        super(message);
    }

    public OperationRollbackException(Throwable cause) {
        super(cause);
    }

    public OperationRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
