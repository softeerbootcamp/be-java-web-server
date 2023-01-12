package io.response;

public class Body {

    private byte[] body = new byte[0];

    public Body() {
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void clear() {
        body = new byte[0];
    }

    @Override
    public String toString() {
        String msg = "";
        for (byte b : body) {
            msg += (char) b;
        }
        return msg;
    }
}
