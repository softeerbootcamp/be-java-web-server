package webserver;

public enum StaticPath {
    TEMPLATE_PATH("./src/main/resources/templates"),
    STATIC_PATH("./src/main/resources/static");

    private String path;

    StaticPath(String s) {
        path = s;
    }
}
