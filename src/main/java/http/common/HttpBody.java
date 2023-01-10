package http.common;

public class HttpBody {
    private byte[] data;

    public HttpBody() {
        this.data = new byte[0];
    }

    public HttpBody(byte[] data) {
        this.data = data;
    }

    public byte[] data() {
        return data;
    }

    public int size() {
        return data.length;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
