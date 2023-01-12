package http.exception;

import http.common.HttpStatus;

public class ForbiddenException extends HttpException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
