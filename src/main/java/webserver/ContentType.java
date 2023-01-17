package webserver;

public enum ContentType {
    HTML(".html","text/html;charset=utf-8"),
    CSS(".css","text/css"),
    JS(".js","application/javascript"),
    FONT(".woff", "application/x-font-woff");

    private String extension;
    private String mimeData;

    ContentType(String extension, String mimeData) {
        this.extension = extension;
        this.mimeData = mimeData;
    }

    public String getExtensionValue() {
        return extension;
    }

    public String getMimeData() {
        return mimeData;
    }
}
