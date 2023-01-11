package http.exception;

import http.common.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
