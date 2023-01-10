package io.request;

import java.util.List;

public class PathParser {

    private static final String TEMPLATE_PATH = "src/main/resources/templates/%s";
    private static final String STATIC_PATH = "src/main/resources/static/%s";
    private static final List<String> statics = List.of("css", "fonts", "images", "js");
    private static final String DOMAIN = "/";
    public static final String NOT_FOUND_HTML = "notfound.html";
    public static final String INDEX_HTML = "index.html";

    public String parse(String url) {
        if (isIndexPage(url)) {
            return getIndexPagePath();
        }
        if (isHtml(url)) {
            return String.format(TEMPLATE_PATH, url);
        }
        if (isStaticResource(url)) {
            return String.format(STATIC_PATH, url);
        }
        return String.format(TEMPLATE_PATH, NOT_FOUND_HTML);
    }

    public String getIndexPagePath() {
        return String.format(TEMPLATE_PATH, INDEX_HTML);
    }

    public String getIndexPageUrl() {
        return DOMAIN;
    }

    private boolean isStaticResource(String url) {
        return statics.contains(getFileExtension(url));
    }

    private boolean isHtml(String url) {
        return url.contains("html");
    }

    private boolean isIndexPage(String url) {
        return url.equals("/") || url.split("/").length == 0;
    }

    private String getFileExtension(String url) {
        String[] chunks = url.split("\\.", 2);
        if (chunks.length >= 2) {
            return chunks[1];
        }
        return "";
    }
}
