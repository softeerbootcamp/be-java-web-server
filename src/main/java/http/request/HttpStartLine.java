package http.request;

import utils.HttpMethod;

public class HttpStartLine {
    private final HttpMethod method;
    private final String target;
    private final String version;

    private HttpStartLine(HttpMethod method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }

    public static HttpStartLine of(HttpMethod method, String target, String version) {
        return new HttpStartLine(method, target, version);
    }

    public String getTarget() {
        return target;
    }

    public boolean hasBody() {
        return method.getHasBody();
    }

    public boolean hasParameter() {
        return this.target.contains("?");
    }

    public String getVersion() {
        return version;
    }

    public String getContentType() {
        if (hasParameter())
            return null;
        return target.substring(target.lastIndexOf(".") + 1);
    }
}
