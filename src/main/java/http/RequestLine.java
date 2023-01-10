package http;

public class RequestLine {

    private final Method method;
    private final Uri uri;
    private final String version;

    private RequestLine(
            Method method,
            Uri uri,
            String version
    ) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        return new RequestLine(
                Method.valueOf(splitRequestLine[0]),
                Uri.from(splitRequestLine[1]),
                splitRequestLine[2]
                );
    }

    public Method getMethod() {
        return method;
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
                "method=" + method +
                ", url='" + uri + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
