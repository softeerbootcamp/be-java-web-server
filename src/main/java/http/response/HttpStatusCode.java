package http.response;

public enum HttpStatusCode {
    OK("200", "OK"),
    CREATED("201", "Created"),
    FOUND("302", "Found"),
    SEE_OTHER("303", "See other"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not Found");

    private final String code;
    private final String message;

    HttpStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
