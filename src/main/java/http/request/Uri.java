package http.request;

import java.util.Map;

public class Uri {
    private final String path;
    private final Map<String, String> querys;

    public Uri(String path, Map<String, String> querys) {
        this.path = path;
        this.querys = querys;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuerys() {
        return querys;
    }
}
