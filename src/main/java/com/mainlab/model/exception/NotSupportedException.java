package com.mainlab.model.exception;

import lombok.Getter;

@Getter
public class NotSupportedException extends RuntimeException {
    private static final long serialVersionUID = -430242495077991699L;

    protected ErrorCode errorCode;

    public NotSupportedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotSupportedException(Throwable cause, ErrorCode errorCode) {
        super(cause.getMessage(), cause);

        if (cause instanceof NotSupportedException) {
            this.errorCode = ((NotSupportedException) cause).getErrorCode();
        } else {
            this.errorCode = errorCode;
        }
    }
}
