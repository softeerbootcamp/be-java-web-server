package http;

public class HttpStartLine {
    private static final String SPACE = " ";
    private static final String ROOT = "/";
    private static final String ENTRY_POINT = "/index.html";

    private final HttpMethod method;
    private final String path;
    private final String version;

    public static HttpStartLine from(String startLine) {
        String[] tokens = startLine.split(SPACE);
        return new HttpStartLine(HttpMethod.valueOf(tokens[0]), tokens[1], tokens[2]);
    }

    private HttpStartLine(HttpMethod method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        if (path.equals(ROOT))
            return ENTRY_POINT;

        return path;
    }

    public String getHttpVersion() {
        return version;
    }

}
