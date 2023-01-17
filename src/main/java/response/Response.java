package response;

import request.Request;

public class Response {
    private final byte[] body;

    private final int httpResponseStatus;

    private final ResponseHeader header;

    private Response(HttpResponseStatus httpResponseStatus) {
        this.body = httpResponseStatus.getMessage().getBytes();
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.of(httpResponseStatus, this.body);
    }

    private Response(byte[] body, Request request, HttpResponseStatus httpResponseStatus) {
        this.body = body;
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.of(httpResponseStatus, request, body);
    }

    private Response(byte[] body, Request request, HttpResponseStatus httpResponseStatus, String optional) {
        this.body = body;
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.of(httpResponseStatus, request, body, optional);
    }

    public static Response of(HttpResponseStatus httpResponseStatus) {
        return new Response(httpResponseStatus);
    }

    public static Response of(byte[] body, Request request, HttpResponseStatus httpResponseStatus) {
        return new Response(body, request, httpResponseStatus);
    }

    public static Response of(byte[] body, Request request, HttpResponseStatus httpResponseStatus, String optional) {
        return new Response(body, request, httpResponseStatus, optional);
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeader() {
        return header.getHeader();
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + httpResponseStatus + '\'' +
                '}';
    }
}
