package http.common;

import java.util.Map;

public class URI {
    private final HttpMethod method;
    private final String path;
    private final Map<String, String> querys;

    public URI(HttpMethod method, String path, Map<String, String> querys) {
        this.method = method;
        this.path = path;
        this.querys = querys;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuerys() {
        return querys;
    }
}
