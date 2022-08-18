package com.mainlab.model.exception;

import lombok.Getter;

@Getter
public class TokenExpiredException extends RuntimeException {
    private static final long serialVersionUID = -5054089493640353365L;

    protected ErrorCode errorCode;

    public TokenExpiredException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TokenExpiredException(Throwable cause, ErrorCode errorCode) {
        super(cause.getMessage(), cause);

        if (cause instanceof TokenExpiredException) {
            this.errorCode = ((TokenExpiredException) cause).getErrorCode();
        } else {
            this.errorCode = errorCode;
        }
    }
}
