package enums;

public enum Status {
    OK("200", "OK"),
    CREATED("201", "Created"),
    REDIRECT("301", "Moved Permanently"),
    NOT_FOUND("404", "Not found");

    private String code;
    private String message;

    Status(String code, String message) {
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
