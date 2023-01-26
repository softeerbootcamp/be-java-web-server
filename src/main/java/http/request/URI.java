package http.request;

import utils.ParseUtils;

import java.util.HashMap;
import java.util.Map;


public class URI {
    private final String path;
    private final Map<String, String> params;

    private URI(String path, Map<String, String> params) {
        this.path = path;
        this.params = params;
    }

    public static URI create(String target) {
        if (target.contains("?"))
            return new URI(parsePath(target), ParseUtils.parseQueryString(target.split("\\?")[1]));
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
                ", params=" + params +
                '}';
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
