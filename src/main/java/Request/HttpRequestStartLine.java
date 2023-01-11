package Request;

public class HttpRequestStartLine {
    private String path;
    private String protocol;
    private String method;

    public HttpRequestStartLine(String method, String path, String protocol) {
        this.path = path;
        this.protocol = protocol;
        this.method = method;
    }

    public String getPath() {
        return path;
    }
}
