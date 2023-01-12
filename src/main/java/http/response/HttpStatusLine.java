package http.response;

public class HttpStatusLine {

    private final String httpVersion;
    private final HttpStatusCode statusCode;

    public HttpStatusLine(String httpVersion, HttpStatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public static HttpStatusLine of(String httpVersion, HttpStatusCode statusCode) {
        return new HttpStatusLine(httpVersion, statusCode);
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return String.join(" ", httpVersion, String.valueOf(statusCode.getStatusCode()), statusCode.getMessage());
    }
}
