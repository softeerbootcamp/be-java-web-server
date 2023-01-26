package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtil;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParams {
    private Map<String, String> params = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParams.class);

    public HttpRequestParams(Map<String, String> params) {
        this.params = params;
    }

    public static HttpRequestParams from(String query) {
        Map<String, String> params = HttpRequestUtil.extractParams(query);
        return new HttpRequestParams(params);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
