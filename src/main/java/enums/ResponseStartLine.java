package enums;

public enum ResponseStartLine {
    PROTOCOL(0),
    STATUS(1),
    MESSAGE(2);

    private Integer order;

    ResponseStartLine(Integer order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
