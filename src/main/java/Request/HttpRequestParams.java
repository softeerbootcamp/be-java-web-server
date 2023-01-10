package Request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParams {
    private Map<String, String> params = new HashMap<>();

    public HttpRequestParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
