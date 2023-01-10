package model.response;

import model.general.Header;

import java.util.Map;

public class Response {
    private final StatusLine statusLine;
    private final Map<Header, String> headers;
    private final byte[] body;

    private Response(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static Response of(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        return new Response(statusLine, headers, body);
    }
}
