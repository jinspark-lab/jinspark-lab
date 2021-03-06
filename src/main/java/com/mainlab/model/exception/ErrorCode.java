package com.mainlab.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST
    BAD_REQUEST(400),

    // 401 UNAUTHORIZED. User does not have valid token.
    UNAUTHORIZED(401),

    // 403 FORBIDDEN. User does have token, but does not have authority.
    FORBIDDEN(403),

    // 404 NOT_FOUND
    NOT_FOUND(404),

    // 500 INTERNAL_ERROR
    INTERNAL_ERROR(500),
    ;

    // 1001+ Custom ErrorCode

    int code;

}
