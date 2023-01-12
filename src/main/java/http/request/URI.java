package http.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class URI {
    private final String path;
    private final Map<String, String> querys;

    private URI(String path, Map<String, String> querys) {
        this.path = path;
        this.querys = querys;
    }

    @Override
    public String toString() {
        return "URI{" +
                "path='" + path + '\'' +
                ", querys=" + querys +
                '}';
    }

    public static URI create(String target){
        return new URI(parsePath(target), parseQueryString(target));
    }

    private static Map<String, String> parseQueryString(String target) {
        Map<String, String> map = new HashMap<>();
        if (!target.contains("?")) {
            return map;
        }
        String queryString = target.split("\\?")[1];
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=", 2);
            String name = URLDecoder.decode(keyValuePair[0], StandardCharsets.UTF_8);
            if (Objects.equals(name, "")) {
                continue;
            }
            String value = keyValuePair.length > 1 ? URLDecoder.decode(
                    keyValuePair[1], StandardCharsets.UTF_8) : "";
            map.put(name, value);
        }
        return map;
    }

    private static String parsePath(String target){
        if (target.equals("/"))
            return "/index.html";
        if (!target.contains("?"))
            return target.trim();
        return target.split("\\?")[0].trim();
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuerys() {
        return querys;
    }
}
