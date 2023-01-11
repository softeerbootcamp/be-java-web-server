package http.exception;

import http.common.HttpStatus;

public abstract class HttpException extends RuntimeException {
    protected HttpStatus status;

    public HttpException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
