package http.request;

import utils.ParseUtils;

import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> params;

    private HttpRequestBody(Map<String, String> params) {
        this.params = params;
    }

    public static HttpRequestBody from(String content) {
        Map<String, String> params = ParseUtils.parseQueryString(content);
        return new HttpRequestBody(params);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
