package http.response;

public class HttpResponseBody {
    private byte[] body;

    private HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static HttpResponseBody createBody(byte[] body) {
        return new HttpResponseBody(body);
    }

    public int getBodySize() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }
}
