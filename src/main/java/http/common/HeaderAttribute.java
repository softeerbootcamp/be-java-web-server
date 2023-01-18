package http.common;

public enum HeaderAttribute {
    CONTENT_TYPE("Content-type", "text/plain"),
    CONTENT_LENGTH("Content-Length", "0"),
    LOCATION("Location", "index.html"),
    SET_COOKIE("Set-Cookie", ""),
    COOKIE("Cookie", "");

    private String value;
    private String defaultValue;

    HeaderAttribute(String value, String defaultValue) {
        this.value = value;
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
