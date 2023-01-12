package http.response;

public class HttpResponseHeaders {
    ContentType contentType;
    private final int contentLength;
    private String location;

    private HttpResponseHeaders(ContentType contentType, int contentLength, String Location) {
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.location = Location;
    }

    public static HttpResponseHeaders createHeaders(ContentType contentType, int contentLength, String location) {
        return new HttpResponseHeaders(contentType, contentLength, location);
    }

    public static HttpResponseHeaders createDefaultHeaders() {
        return new HttpResponseHeaders(null, 0, null);
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getLocation() {
        return location;
    }

    public void setContentType(ContentType contentType) {

    }

    public void setContentLength(int contentLength) {

    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Content-Type: " + contentType + System.lineSeparator()
                + "Content-Length: " + contentLength;
    }

}
