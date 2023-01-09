package enums;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript");

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
