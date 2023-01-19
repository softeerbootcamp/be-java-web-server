package response;

import request.HttpVersion;

public class StatusLine {
    private final HttpVersion httpVersion;
    private final StatusCode statusCode;

    public StatusLine(HttpVersion httpVersion, StatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public String getValue() {
        return String.format("%s %s", httpVersion.getVersion(), statusCode.getValue());
    }
}
