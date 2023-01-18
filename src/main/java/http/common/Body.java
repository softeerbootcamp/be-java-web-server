package http.common;

public class Body {

    private byte[] data;

    public Body() {
        this.data = new byte[0];
    }

    public Body(String data) {
        this.data = toByteArray(data);
    }

    private byte[] toByteArray(String data) {
        byte[] bytes = new byte[data.length()];
        for (int i = 0; i < data.length(); i++) {
            bytes[i] = (byte) data.charAt(i);
        }
        return bytes;
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

    public boolean isEmpty() {
        return data.length == 0;
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
