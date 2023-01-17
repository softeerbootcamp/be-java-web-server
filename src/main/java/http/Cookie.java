package http;

public class Cookie {

    private final String name;
    private final String value;

    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie of(String name, String value) {
        return new Cookie(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.value;
    }

}
