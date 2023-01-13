package http.response;

public class HttpStatusLine {

    private static final String SPACE = " ";

    private final String httpVersion;
    private HttpStatusCode httpStatusCode;

    public HttpStatusLine(String httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpStatusLine createDefaultStatusLine() {
        return new HttpStatusLine("HTTP/1.1", HttpStatusCode.OK);
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return httpVersion
                + SPACE
                + httpStatusCode.getCode()
                + SPACE
                + httpStatusCode.getMessage()
                + System.lineSeparator();
    }

}
