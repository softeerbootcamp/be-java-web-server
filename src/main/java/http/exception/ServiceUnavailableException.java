package http.exception;

import http.common.HttpStatus;

public class ServiceUnavailableException extends HttpException {
    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
