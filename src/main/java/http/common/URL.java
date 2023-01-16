package http.common;

import java.util.Map;

public class URL {
    private final String path;
    private final Map<String, String> queries;

    public URL(String path, Map<String, String> queries) {
        this.path = path;
        this.queries = queries;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}
