package enums;

public enum MethodEnums {
    GET("GET"),
    POST("POST");
    private final String value;

    MethodEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
