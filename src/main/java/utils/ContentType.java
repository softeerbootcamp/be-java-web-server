package utils;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    FAVICON("ico", "image/ico"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff");

    private final String extension;
    private final String type;

    ContentType(String extension, String type) {
        this.extension = extension;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static ContentType getContentType(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 타입입니다.");
        }
    }
}
