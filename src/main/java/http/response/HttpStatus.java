package http.response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String tag;

    HttpStatus(int code, String tag) {
        this.code = code;
        this.tag = tag;
    }

    public int getCode() {
        return code;
    }

    public String getTag() {
        return tag;
    }

    public String toMessage() {
        return code + " " + tag;
    }
}
