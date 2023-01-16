package http.response.enums;

public enum ResponseAttribute {
    CONTENT_TYPE("Content-type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private String value;

    ResponseAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
