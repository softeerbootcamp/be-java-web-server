package util;

import webserver.ContentType;

public class PathUtils {
    public static String pathEndCheck(String path) {
        if (path.endsWith(ContentType.HTML.getExtensionValue())) {
            return ContentType.HTML.getMimeData();
        }
        if (path.endsWith(ContentType.CSS.getExtensionValue())) {
            return ContentType.CSS.getMimeData();
        }
        if (path.endsWith(ContentType.CSS.getExtensionValue())) {
            return ContentType.JS.getMimeData();
        }
        return "text/plain";
    }
}
