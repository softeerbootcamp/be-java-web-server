package enums;

public enum ContentTypeEnum {
    HTML("text/html"),
    CSS("text/css"),
    PLAIN("text/plain"),
    JAVASCRIPT("text/javascript"),
    ;
    private final String value;
    ContentTypeEnum(String value) {
        this.value = value;
    }
}
