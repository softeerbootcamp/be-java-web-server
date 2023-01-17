package http.common;

public class Body {

    private byte[] data;

    public Body(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setBody(byte[] data) {
        this.data = data;
    }

    public void clear() {
        data = new byte[0];
    }

    @Override
    public String toString() {
        String msg = "";
        for (byte b : data) {
            msg += (char) b;
        }
        return msg;
    }
}
