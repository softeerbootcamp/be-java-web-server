package http.cookie;

public class Cookie {

    public static final String COOKIE_SUFFIX = "; path=/";

    private final String name;
    private final String value;

    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value + COOKIE_SUFFIX);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name + "=" + this.value;
    }

}
