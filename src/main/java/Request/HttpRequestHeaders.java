package Request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {
    private Map<String, String> headers = new HashMap<>();

    public HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
