package webserver.httpMock.constants;

public enum ContentType {
    TEXT_HTML("text/html");

    private final String contentType;

    private ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
