package utils;

public enum StatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    MOVEDPERMANENTLY(301, "Moved Permanently"),
    SEEOTHER(302, "See Other"),
    BADREQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOTFOUND(404, "Not Found");

    private int statusCode;
    private String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static StatusCode getStatusCode(String message) {
        try {
            return valueOf (message.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 상태 메세지입니다.");
        }
    }

    @Override
    public String toString() {
        return statusCode + " " + statusMessage;
    }
}
