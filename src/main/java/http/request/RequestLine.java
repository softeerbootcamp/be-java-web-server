package http.request;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final HttpUri httpUri;
    private final String version;

    private RequestLine(
            HttpMethod httpMethod,
            HttpUri httpUri,
            String version
    ) {
        this.httpMethod = httpMethod;
        this.httpUri = httpUri;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        return new RequestLine(
                HttpMethod.valueOf(splitRequestLine[0]),
                HttpUri.from(splitRequestLine[1]),
                splitRequestLine[2]
                );
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + httpMethod +
                ", url='" + httpUri + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String getUrl() {
        return httpUri.getPath();
    }
}
