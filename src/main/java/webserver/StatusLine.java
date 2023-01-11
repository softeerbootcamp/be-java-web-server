package webserver;

import enums.HttpStatus;

public class StatusLine {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private HttpStatus httpStatus;

    public StatusLine(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
