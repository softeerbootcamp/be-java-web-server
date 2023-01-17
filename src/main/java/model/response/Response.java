package model.response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private StatusLine statusLine;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public void setStatusCode(String version, HttpStatusCode httpStatusCode) {
        this.statusLine = new StatusLine(version, httpStatusCode);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
