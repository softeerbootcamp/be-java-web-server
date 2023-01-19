package webserver;

public enum ContentType {
    HTML("text/html", "charset=utf-8", ""),
    CSS("text/css", "", ""),
    JS("application/javascript", "", ""),
    TXT("text/plain", "", ""),
    APPLICATION_JSON("application/json", "", ""),
    ICO("image/x-icon", "", ""),
    TTF("font/ttf", "", ""),
    WOFF("font/woff", "", ""),
    WOFF2("font/woff2", "", "");

    private final String mediaType;
    private final String charset;
    private final String boundary; // message 보낼때 구분자로 사용

    ContentType(String mediaType, String charset, String boundary) {
        this.mediaType = mediaType;
        this.charset = charset;
        this.boundary = boundary;
    }

    public String value() {
        String charsetToAppend = (charset == "") ? "" : (";" + charset);
        String boundaryToAppend = (boundary == "") ? "" : ( ";" + boundary);

        String value = mediaType + charsetToAppend + boundaryToAppend;

        return value;
    }
}
