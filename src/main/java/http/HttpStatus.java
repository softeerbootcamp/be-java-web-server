package http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND");

    private final int statusCode;
    private final String status;

    HttpStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public static HttpStatus of(int statusCode) {
        return Arrays.stream(HttpStatus.values())
                .filter(httpStatus -> httpStatus.statusCode == statusCode)
                .findAny()
                .orElseThrow();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return statusCode + " " + status;
    }
}
