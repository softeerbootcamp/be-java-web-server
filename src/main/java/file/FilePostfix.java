package file;

public enum FilePostfix {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    ICO("ico", "image/x-icon"),
    JS("js", "text/javascript"),
    EOT("eot","application/vnd.ms-fontobject"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "application/x-font-ttf"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    NO_MATCH("","");

    private String postfix;

    private String contentType;

    private FilePostfix(String postfix, String contentType) {
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
