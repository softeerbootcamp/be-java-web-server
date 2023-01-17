package http;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final Uri uri;
    private final String version;

    private RequestLine(
            HttpMethod httpMethod,
            Uri uri,
            String version
    ) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        return new RequestLine(
                HttpMethod.valueOf(splitRequestLine[0]),
                Uri.from(splitRequestLine[1]),
                splitRequestLine[2]
                );
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Uri getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + httpMethod +
                ", url='" + uri + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
