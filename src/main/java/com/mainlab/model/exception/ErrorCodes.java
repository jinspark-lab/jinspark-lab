package com.mainlab.model.exception;

import java.util.function.Predicate;

public class ErrorCodes {

    public static void checkCondition(boolean condition, ErrorCode errorCode, String message) {
        if (!condition) {
            throw new BaseRuntimeException(message, errorCode);
        }
    }

    public static void checkCondition(boolean condition, ErrorCode errorCode, String message, Class exceptionClass) {
        if (!condition) {
            if (exceptionClass == BaseRuntimeException.class) {
                throw new BaseRuntimeException(message, errorCode);
            } else if (exceptionClass == AuthorizationException.class) {
                throw new AuthorizationException(message, errorCode);
            } else if (exceptionClass == TokenExpiredException.class) {
                throw new TokenExpiredException(message, errorCode);
            } else {
                throw new BaseRuntimeException(message, errorCode);
            }
        }
    }

    public static <T> void checkPredicate(Predicate<T> predicate, T t, ErrorCode errorCode, String message) {
        if (!predicate.test(t)) {
            throw new BaseRuntimeException(message, errorCode);
        }
    }
}
