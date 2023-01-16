package model;

import java.util.HashMap;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

public class Request {

    private final String method;
    private final String url;
    private final Map<String, String> requestParams;


    public Request(String line) {
        String[] tokens = line.split(" ");
        this.method = tokens[0];

        String url = tokens[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        this.url = url;
        this.requestParams = createRequestParams();
    }

    public Map<String, String> createRequestParams() {
        String[] queryStrings = url.split("\\?");
        if (queryStrings.length != 1) {
            String queryString = queryStrings[1];

            return new HashMap<>(parseQuerystring(queryString));
        }
        return Map.of();
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }
}
