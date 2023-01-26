package response;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    WOFF("application/font-woff"),
    TTF("application/font-ttf"),
    EOT("application/vnd.ms-fontobject"),
    OTF("application/font-otf"),
    SVG("image/svg+xml"),
    PNG("image/jpeg"),
    ICO("image/x-icon"),
    PLAIN("text/plain");
    private String contentText;

    ContentType(String contentText) {
        this.contentText = contentText;
    }

    public String getContentText() {
        return contentText + ";charset=UTF-8";
    }
}
