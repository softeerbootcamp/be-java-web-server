package model.response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private StatusLine statusLine;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public static Response of(String version, HttpStatusCode httpStatusCode, Map<String, String> headers, byte[] body) {
        Response response = new Response();
        response.statusLine = new StatusLine(version, httpStatusCode);
        response.headers.putAll(headers);
        response.body = body;
        return response;
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
