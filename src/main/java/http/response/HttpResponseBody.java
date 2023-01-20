package http.response;

public class HttpResponseBody {

    public static final int EMPTY_LENGTH = 0;

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

    public boolean hasBody() {
        return body.length != EMPTY_LENGTH;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    private static byte[] getEmptyBody(){
        return "".getBytes();
    }

}
