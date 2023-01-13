package http.exception;

import http.common.HttpStatus;

public class MethodNotAllowException extends HttpException {
    private static final String ERROR_MESSAGE = "Method Not Allowed";

    public MethodNotAllowException() {
        super(ERROR_MESSAGE, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
