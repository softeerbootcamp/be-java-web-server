package util;

import java.util.Map;

public class PathResolver {

    private static final Map<String, String> RESOURCE_PATH = Map.of(
            "/", "templates/index.html"
    );
    public static String getResourcePath(String url) {
        return RESOURCE_PATH.get(url);
    }
}
