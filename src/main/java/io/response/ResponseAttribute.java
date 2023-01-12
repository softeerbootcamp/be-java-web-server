package enums;

public enum ResponseAttribute {
    CONTENT_TYPE("Content-type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private String attribute;

    ResponseAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
