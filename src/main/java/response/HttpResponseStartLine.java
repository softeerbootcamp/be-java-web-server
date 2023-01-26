package response;

import request.StatusCode;

import static response.HttpResponse.NEWLINE;

public class HttpResponseStartLine {
    private StatusCode status;
    private String protocol;

    public HttpResponseStartLine(StatusCode status, String protocol) {
        this.status = status;
        this.protocol = protocol;
    }

    public StatusCode getStatus() {
        return status;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return this.protocol + " " + this.status.toString() + NEWLINE;
    }
}
