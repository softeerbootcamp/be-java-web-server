package model.request;

public class RequestLine {
    private final String version;
    private final String method;
    private final String url;

    public RequestLine(String reqLine) {
        String[] tokens = reqLine.split(" ");
        this.method = tokens[0];

        String url = tokens[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        this.url = url;
        this.version = tokens[2];
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }
}
