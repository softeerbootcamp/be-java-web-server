package http;

public class HttpStartLine {
    private static final String SPACE = " ";

    private final HttpMethod method;
    private final Uri uri;
    private final String version;

    public static HttpStartLine from(String startLine) {
        String[] tokens = startLine.split(SPACE);
        HttpMethod httpMethod = HttpMethod.getHttpMethod(tokens[0]);
        Uri uri = Uri.from(tokens[1]);
        String version = tokens[2];
        return new HttpStartLine(httpMethod, uri, version);
    }

    private HttpStartLine(HttpMethod method, Uri uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Uri getUri() {
       return uri;
    }

    public String getHttpVersion() {
        return version;
    }

}
