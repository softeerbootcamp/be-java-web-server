package enums;

public enum Status {
    OK(200),
    NOT_FOUND(404);

    private Integer code;

    Status(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
