package enums;

public enum StatusCodeWithMessageEnum {
    CODE_200("200","Ok"),
    CODE_302("302","Found"),
    CODE_404("404","Not Found"),
    ;

    private final String key;
    private final String value;
    StatusCodeWithMessageEnum(String key,String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
