package webserver;

public enum Paths {
    TEMPLATE_PATH("./src/main/resources/templates"),
    STATIC_PATH("./src/main/resources/static");

    private String path;

    Paths(String s) {
        path = s;
    }

    public String getPath()
    {
        return path;
    }
}
