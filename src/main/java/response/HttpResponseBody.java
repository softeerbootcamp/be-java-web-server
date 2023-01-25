package response;

import util.HttpResponseUtil;

public class HttpResponseBody {
    private byte[] body;

    public HttpResponseBody(byte[] body) {
        this.body = body;
    }
    public static HttpResponseBody from(String requestPath) {
        try {
            byte[] body = HttpResponseUtil.generateBytesBody(requestPath);
            return new HttpResponseBody(body);
        } catch (RuntimeException e){
            return new HttpResponseBody("".getBytes());
        }
    }
    public int getLength() {
        return body.length;
    }
    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
