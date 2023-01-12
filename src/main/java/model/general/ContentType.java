package model.general;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html", ".html"),
    CSS("text/css", ".css"),
    JS("text/javascript", ".js"),
    WOFF("text/font", ".woff"),
    TTF("text/font", ".ttf"),
    ICO("image/x-icon", ".ico"),
    PNG("image/png", ".png");

    private final String contentTypeValue;
    private final String extension;

    ContentType(String contentTypeValue, String extension) {
        this.contentTypeValue = contentTypeValue;
        this.extension = extension;
    }

    public static ContentType from(String extension) {
        return Arrays.stream(values())
                .filter(c -> c.extension.equals(extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Supported Content Type"));
    }

    public String getContentTypeValue() {
        return contentTypeValue;
    }
}
