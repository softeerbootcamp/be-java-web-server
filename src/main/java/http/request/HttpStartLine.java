package http.request;

import utils.HttpMethod;


public class HttpStartLine {
    private final HttpMethod method;
    private final URI uri;
    private final String version;

    private HttpStartLine(HttpMethod method, URI uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static HttpStartLine of(HttpMethod method, URI uri, String version) {
        return new HttpStartLine(method, uri, version);
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
}
