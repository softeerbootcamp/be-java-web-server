package http.request;

import http.common.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private RequestStartLine startLine;
    private Header header;
    private Body body;
    private RequestParameter requestParameter;

    public HttpRequest(RequestStartLine startLine, Header header, Body body) {
        this.startLine = startLine;
        this.header = header;
        this.body = body;
        this.requestParameter = new RequestParameter(startLine, body);
    }

    public String getUrl() {
        return startLine.getUrl();
    }

    public Method getMethod() {
        return startLine.getMethod();
    }

    public List<Cookie> getCookies() {
        return Cookie.cookify(header.getAttribute(HeaderAttribute.COOKIE));
    }

    public Optional<Cookie> getCookie(String key) {
        return getCookies().stream()
                .filter(c -> c.getKey().equals(key))
                .findAny();
    }

    public Map<String, String> getParameters(String... args) {
        Map<String, String> results = new HashMap<>();
        for (String arg : args) {
            results.put(arg, requestParameter.getParameter(arg));
        }
        return results;
    }
}
