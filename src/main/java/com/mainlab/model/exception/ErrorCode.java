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


    // 1001+ Custom ErrorCode
    AUTH_OVERDUE(1001),
    MALFORMED_REQUEST(1002),
    RESOURCE_UPLOAD_FAIL(1003),

    USER_APP_NOT_EXIST(2001),
    USER_APP_SHORTCUT_NOT_EXIST(2002),
    USER_APP_INVALID_REQUEST(2003),

    INVALID_CONTENT(3001),
    CONTENT_NOT_EXISTS(3004),

    USER_BLOG_NOT_EXIST(4001),

    NOT_SUPPORTED(9001),
    INVALID_CONFIG(9002),
    ;

    int code;

}
