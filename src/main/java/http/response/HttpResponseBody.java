package http.response;

public class HttpResponseBody {
    private byte[] body;

    private HttpResponseBody(byte[] bytes) {
        this.body = bytes;
    }

    public static HttpResponseBody createDefaultBody() {
        return new HttpResponseBody(null);
    }

    public static HttpResponseBody createBody(byte[] body) {
        return new HttpResponseBody(body);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

}
