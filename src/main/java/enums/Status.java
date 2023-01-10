package enums;

public enum Status {
    OK("200", "OK"),
    NOT_FOUND("404", "Not found"),
    REDIRECT("301", "Moved Permanently");

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
