package http.common;

public class Cookie {

    private static final String ROOT_PATH = "/";
    private String key;
    private String value;
    private String path;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
        this.path = ROOT_PATH;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value + "; " + "path=" + path;
    }
}
