package http;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html", "./templates", ".html"),
    ICO("image/x-icon", "./templates", ".ico"),
    CSS("text/css", "./static", ".css"),
    JAVASCRIPT("text/javascript", "./static", ".js"),
    PNG("image/jpeg", "./static", ".png"),
    TTF("application/font-ttf", "./static", ".ttf"),
    WOFF("application/font-woff", "./static", ".woff");

    private final String contentType;
    private final String directory;
    private final String extender;

    ContentType(String contentType, String directory, String extender) {
        this.contentType = contentType;
        this.directory = directory;
        this.extender = extender;
    }

    public static ContentType from(String path) {
        return Arrays.stream(values())
                .filter(contentType -> path.endsWith(contentType.extender))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getDirectory() {
        return directory;
    }

    public String getContentType() {
        return contentType;
    }
}
