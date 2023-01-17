package enums;

public enum Status {
    OK("200", "OK"),
    CREATED("201", "Created"),
    FOUND("302", "Found"),
    NOT_FOUND("404", "Not Found");

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
