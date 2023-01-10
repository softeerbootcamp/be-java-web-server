package io.request;

public class PathResolver {

    private static final String ABSOLUTE_PATH = "/Users/rentalhub/Desktop/2주차/src/main/resources/templates/%s";

    public String resolve(String url) {
        if (url.equals("/")) {
            return String.format(ABSOLUTE_PATH, "index.html");
        }
        return String.format(ABSOLUTE_PATH, "notfound.html");
    }
}
