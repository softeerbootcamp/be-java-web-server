package model;

import java.util.HashMap;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

public class Request {


    private final String url;
    private final Map<String, String> requestParams = new HashMap<>();


    public Request(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void checkUrlQueryString() {
        String[] queryStrings = url.split("\\?");
        if (queryStrings.length != 1) {
            String queryString = queryStrings[1];

            requestParams.putAll(parseQuerystring(queryString));
        }
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }
}
