package io.request;

import java.util.List;
import java.util.Map;

public class PathParser {

    private static final String TEMPLATE_PATH = "src/main/resources/templates%s";
    private static final String STATIC_PATH = "src/main/resources/static%s";
    private static final String DOMAIN = "/";
    private static final String INDEX_HTML = "index.html";
    public static final String NOT_FOUND_HTML = "notfound.html";

    private static final Map<List<String>, String> mappingInfo = Map.of(
            List.of("/"), TEMPLATE_PATH + INDEX_HTML,
            List.of(".html"), TEMPLATE_PATH,
            List.of(".css", ".js", ".css", ".js", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png"), STATIC_PATH,
            List.of("*"), NOT_FOUND_HTML
    );

    public String parse(String url) {
        List<String> extension = mappingInfo.keySet().stream()
                .filter(keys -> keys.stream().anyMatch(url::endsWith)
                ).findAny()
                .orElse(List.of("*"));
        return String.format(mappingInfo.get(extension), url);
    }

    public String getIndexPageUrl() {
        return DOMAIN;
    }
}
