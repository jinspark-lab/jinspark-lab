package com.mainlab.model.exception;

import lombok.Getter;

@Getter
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 3185286521484613267L;

    protected ErrorCode errorCode;

    public BaseRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(Throwable cause, ErrorCode errorCode) {
        super(cause.getMessage(), cause);

        if (cause instanceof BaseRuntimeException) {
            this.errorCode = ((BaseRuntimeException) cause).getErrorCode();
        } else {
            this.errorCode = errorCode;
        }
    }
}
