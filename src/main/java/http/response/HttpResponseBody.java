package http.response;

import static utils.FileIoUtils.getEmptyBody;

public class HttpResponseBody {
    private byte[] body;

    private HttpResponseBody(byte[] bytes) {
        this.body = bytes;
    }

    public static HttpResponseBody createDefaultBody() {
        byte[] emptyBody = getEmptyBody();
        return new HttpResponseBody(emptyBody);
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
