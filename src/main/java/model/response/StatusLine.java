package model.response;

public class StatusLine {
    private final String version;
    private final HttpStatusCode httpStatusCode;

    public StatusLine(String version, HttpStatusCode httpStatusCode) {
        this.version = version;
        this.httpStatusCode = httpStatusCode;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
