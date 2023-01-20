package http.request;

import utils.enums.HttpMethod;


public class HttpRequestLine {
    private final HttpMethod method;
    private final URI uri;
    private final String version;

    private HttpRequestLine(HttpMethod method, URI uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static HttpRequestLine of(HttpMethod method, URI uri, String version) {
        return new HttpRequestLine(method, uri, version);
    }

    public boolean hasBody() {
        return method.getHasBody();
    }

    public String getVersion() {
        return version;
    }

    public URI getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
