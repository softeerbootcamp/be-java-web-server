package enums;

public enum HttpVersionTypeEnum {
    HTTP1_1("HTTP/1.1");

    private final String value;

    HttpVersionTypeEnum(String value){
        this.value = value;

    }

    public String getValue() {
        return value;
    }
}
