package io.request;

import controller.FacadeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PathParser {

    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private static final String TEMPLATE_ABSOLUTE_PATH = "/Users/rentalhub/Desktop/2주차/src/main/resources/templates/%s";
    private static final String STATIC_ABSOLUTE_PATH = "/Users/rentalhub/Desktop/2주차/src/main/resources/static/%s";
    private static final List<String> statics = List.of("css", "fonts", "images", "js");

    public String parse(String url) {
        if (isIndexPage(url)) {
            return String.format(TEMPLATE_ABSOLUTE_PATH, "index.html");
        }
        if (isHtml(url)) {
            return String.format(TEMPLATE_ABSOLUTE_PATH, url);
        }
        if (isStaticResource(url)) {
            return String.format(STATIC_ABSOLUTE_PATH, url);
        }
        return String.format(TEMPLATE_ABSOLUTE_PATH, "notfound.html");
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
        return url.split("\\.", 2)[1];
    }
}
