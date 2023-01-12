package enums;

public enum HeaderReferenceEnum {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private final String value;
    HeaderReferenceEnum(String value){
        this.value = value;
    }

    public String getValueWithSpace() {
        return value+": ";
    }
}
