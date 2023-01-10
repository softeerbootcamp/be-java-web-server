package enums;

public enum ResponseAttribute {
    CONTENT_TYPE("content-type"),
    LOCATION("Location");

    private String attribute;

    ResponseAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
