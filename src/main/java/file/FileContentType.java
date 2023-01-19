package file;

public enum FileContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    ICO("ico", "image/x-icon"),
    JS("js", "text/javascript"),
    EOT("eot","application/vnd.ms-fontobject"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "application/x-font-ttf"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    NO_MATCH("","text/plain");

    private final String postfix;

    private final String contentType;

    FileContentType(String postfix, String contentType) {
        this.postfix = postfix;
        this.contentType = contentType;
    }

    public String getPostfix() {
        return this.postfix;
    }

    public String getContentType() {
        return this.contentType;
    }
}
