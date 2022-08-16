package com.mainlab.model.exception;

import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {
    private static final long serialVersionUID = -4467777254741542643L;

    protected ErrorCode errorCode;

    public AuthorizationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AuthorizationException(Throwable cause, ErrorCode errorCode) {
        super(cause.getMessage(), cause);

        if (cause instanceof AuthorizationException) {
            this.errorCode = ((AuthorizationException) cause).getErrorCode();
        } else {
            this.errorCode = errorCode;
        }
    }
}
