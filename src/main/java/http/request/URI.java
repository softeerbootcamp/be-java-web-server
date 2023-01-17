package http.request;

import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;


public class URI {
    private final String path;
    private final Map<String, String> query;

    private URI(String path, Map<String, String> query) {
        this.path = path;
        this.query = query;
    }

    public static URI create(String target) {
        if (target.contains("?"))
            return new URI(parsePath(target), FileIoUtils.parseQueryString(target.split("\\?")[1]));
        return new URI(parsePath(target), new HashMap<>());
    }

    private static String parsePath(String target) {
        if (target.equals("/"))
            return "/index.html";
        if (!target.contains("?"))
            return target.trim();
        return target.split("\\?")[0].trim();
    }

    @Override
    public String toString() {
        return "URI{" +
                "path='" + path + '\'' +
                ", query=" + query +
                '}';
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}
