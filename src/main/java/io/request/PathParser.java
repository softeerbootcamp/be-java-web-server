package io.request;

import java.util.Map;

public class PathParser {

    private static final String TEMPLATE_PATH = "src/main/resources/templates%s";
    private static final String STATIC_PATH = "src/main/resources/static%s";
    private static final String DOMAIN = "/";
    public static final String NOT_FOUND_HTML = "notfound.html";
    public static final String INDEX_HTML = "index.html";

    private static final Map<String, String> mappingInfo = Map.of(
            "/", TEMPLATE_PATH + INDEX_HTML,
            ".html", TEMPLATE_PATH,
            ".css", STATIC_PATH,
            ".js", STATIC_PATH,
            "*", NOT_FOUND_HTML
    );

    public String parse(String url) {
        String extension = mappingInfo.keySet().stream().filter(key -> url.endsWith(key)).findAny().orElse("*");
        return String.format(mappingInfo.get(extension), url);
    }

    public String getIndexPageUrl() {
        return DOMAIN;
    }
}
