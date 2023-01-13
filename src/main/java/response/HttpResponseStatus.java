package response;

public enum HttpResponseStatus {
    OK("200 OK"),
    FOUND("302 FOUND"),
    NOT_FOUND("404 Not Found");

    private String message;

    private HttpResponseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
