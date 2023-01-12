package http.response;

public class HttpResponseBody {
    private byte[] body;

    private HttpResponseBody(byte[] bytes) {
        this.body = bytes;
    }

    public static HttpResponseBody createDefaultBody() {
        return new HttpResponseBody("".getBytes());
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

}
