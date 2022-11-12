package com.mainlab.controller;

import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.response.FailureResponse;
import com.mainlab.service.AppLogService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController {

    @Autowired
    private AppLogService appLogService;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseRuntimeException.class)
    public FailureResponse baseRuntimeException(BaseRuntimeException e) {
        appLogService.error(e.getMessage());
        return new FailureResponse(ErrorCode.BAD_REQUEST.getCode(), e.getMessage(), DateTime.now().getMillis());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class, AuthorizationException.class})
    public FailureResponse accessDeniedException(AccessDeniedException e) {
        appLogService.error(e.getMessage());
        return new FailureResponse(ErrorCode.UNAUTHORIZED.getCode(), e.getMessage(), DateTime.now().getMillis());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public FailureResponse runtimeException(RuntimeException e) {
        appLogService.error(e.getMessage());
        return new FailureResponse(ErrorCode.INTERNAL_ERROR.getCode(), "Internal Server Error", DateTime.now().getMillis());
    }
}
