package http;

public class RequestLine {

    private final Method method;
    private final Uri uri;
    private final String version;

    public RequestLine(String requestLine) {
        String[] split = requestLine.split(" ");
        this.method = Method.valueOf(split[0]);
        this.uri = new Uri(split[1]);
        this.version = split[2];
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
