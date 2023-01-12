package io.request;

import io.request.startLine.StartLine;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private StartLine startLine;
    private Header header;

    private RequestParameter requestParameter;

    public HttpRequest(StartLine startLine, Header header, RequestParameter requestParameter) {
        this.startLine = startLine;
        this.header = header;
        this.requestParameter = requestParameter;
    }

    public String getUrl() {
        return startLine.getUrl();
    }

    public Map<String, String> getParameters(String... args) {
        Map<String, String> results = new HashMap<>();
        for (String arg : args) {
            results.put(arg, requestParameter.getParameter(arg));
        }
        return results;
    }
}
