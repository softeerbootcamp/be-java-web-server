package http;

public class RequestLine {

    private final Method method;
    private final String url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] split = requestLine.split(" ");
        this.method = Method.valueOf(split[0]);
        this.url = split[1];
        this.version = split[2];
    }

    public String getUrl() {
        return url;
    }


    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
