package model.response;

import model.general.Status;

public class StatusLine {
    private final String httpVersion;
    private final Status status;

    private StatusLine(String httpVersion, Status status) {
        this.httpVersion = httpVersion;
        this.status = status;
    }

    public static StatusLine of(String httpVersion, Status status) {
        return new StatusLine(httpVersion, status);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getStatusCode() {
        return String.valueOf(status.getStatusCode());
    }

    public String getReasonPhrase() {
        return status.getReasonPhrase();
    }

    public String getStatusLine() {
        return String.join(" ",
                new String[]{httpVersion, String.valueOf(status.getStatusCode()), status.getReasonPhrase()});
    }
}
