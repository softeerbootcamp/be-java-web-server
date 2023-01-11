package http.response;

public class HttpStatusLine {

    private final String httpVersion;
    private final HttpStatusCode statusCode;

    public HttpStatusLine(String httpVersion, HttpStatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }
}
