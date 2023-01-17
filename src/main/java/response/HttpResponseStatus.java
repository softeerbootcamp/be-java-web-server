package response;

public enum HttpResponseStatus {
    OK(200, "200 OK"),
    FOUND(302, "302 FOUND"),
    BAD_REQUEST(400, "400 BAD REQUEST"),
    NOT_FOUND(404, "404 Not Found");

    private String message;

    private int code;

    private HttpResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
