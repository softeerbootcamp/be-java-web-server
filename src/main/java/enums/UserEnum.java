package enums;

public enum UserEnum {
    ID("userid"),
    PW("password"),
    NAME("name"),
    EMAIL("email");
    private String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
