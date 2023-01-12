package response;

public class HttpResponseBody {
    private final byte[] body;

    private HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public static HttpResponseBody of(byte[] body) {
        return new HttpResponseBody(body);
    }

    public int length() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }
}
