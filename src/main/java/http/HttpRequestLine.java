package http;

import util.HttpRequestUtils;

public class HttpRequestLine {

    private final HttpMethod method;
    private final String url;
    private final String version;

    public HttpRequestLine(String requestLine) {
        this.method = HttpRequestUtils.getHttpMethod(requestLine);
        this.url = HttpRequestUtils.getUrl(requestLine);
        this.version = HttpRequestUtils.getHttpVersion(requestLine);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
