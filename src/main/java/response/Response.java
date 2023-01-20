package response;

public class Response {
    private final byte[] body;

    private final int httpResponseStatus;

    private final ResponseHeader header;

    private Response(HttpResponseStatus httpResponseStatus) {
        this.body = httpResponseStatus.getMessage().getBytes();
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.of(this.body, httpResponseStatus);
    }

    private Response(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        this.body = body;
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.createSimpleRespHeader(body, contentType, httpResponseStatus);
    }

    private Response(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        this.body = body;
        this.httpResponseStatus = httpResponseStatus.getCode();
        this.header = ResponseHeader.createFullRespHeader(body, contentType, httpResponseStatus, optional);
    }

    public static Response from(HttpResponseStatus httpResponseStatus) {
        return new Response(httpResponseStatus);
    }

    public static Response createSimpleResponse(byte[] body, String contentType, HttpResponseStatus httpResponseStatus) {
        return new Response(body, contentType, httpResponseStatus);
    }

    public static Response createFullResponse(byte[] body, String contentType, HttpResponseStatus httpResponseStatus, String optional) {
        return new Response(body, contentType, httpResponseStatus, optional);
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
