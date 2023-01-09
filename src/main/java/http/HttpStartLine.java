package http;

public class HttpStartLine {
    private final String method;
    private final String target;
    private final String version;

    private HttpStartLine(String method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }

    public static HttpStartLine of(String method, String target, String version) {
        return new HttpStartLine(method, target, version);
    }

    public String getTarget() {
        return target;
    }
}
