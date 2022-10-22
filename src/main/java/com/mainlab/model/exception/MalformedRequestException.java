package com.mainlab.model.exception;


import lombok.Getter;

@Getter
public class MalformedRequestException extends RuntimeException {
    private static final long serialVersionUID = -4467777254741542643L;

    protected ErrorCode errorCode;

    public MalformedRequestException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MalformedRequestException(Throwable cause, ErrorCode errorCode) {
        super(cause.getMessage(), cause);

        if (cause instanceof MalformedRequestException) {
            this.errorCode = ((MalformedRequestException) cause).getErrorCode();
        } else {
            this.errorCode = errorCode;
        }
    }
}
