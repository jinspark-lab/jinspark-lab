package com.mainlab.controller;

import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.response.FailureResponse;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseRuntimeException.class)
    public FailureResponse baseRuntimeException(BaseRuntimeException e) {
        return new FailureResponse(ErrorCode.BAD_REQUEST.getCode(), e.getMessage(), DateTime.now().getMillis());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class, AuthorizationException.class})
    public FailureResponse accessDeniedException(AccessDeniedException e) {
        return new FailureResponse(ErrorCode.UNAUTHORIZED.getCode(), e.getMessage(), DateTime.now().getMillis());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public FailureResponse runtimeException(RuntimeException e) {
        //FIXME: Hide Error Message in Detail
        return new FailureResponse(ErrorCode.INTERNAL_ERROR.getCode(), e.getMessage(), DateTime.now().getMillis());
    }
}
