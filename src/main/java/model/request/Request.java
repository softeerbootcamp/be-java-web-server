package model.request;

import model.general.Header;

import java.util.Map;

public class Request {
    private final RequestLine requestLine;
    private final Map<Header, String> headers;
    private final byte[] body;

    private Request(RequestLine requestLine, Map<Header, String> headers, byte[] body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static Request of(RequestLine requestLine, Map<Header, String> headers) {
        return new Request(requestLine, headers, null);
    }
}
