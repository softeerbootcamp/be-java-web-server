package model;

import java.util.HashMap;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

public class Request {

    private final String url;
    private final Map<String, String> requestParams = new HashMap<>();


    public Request(String line) {
        this.url = extractUrl(line);
        this.checkUrlQueryString();
    }

    private String extractUrl(String line) {
        //문자열 분리 후 -> 문자열 배열에 삽입
        String[] tokens = line.split(" ");
        String url = tokens[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
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
