package webserver.domain;

public class HttpResponseMessage {
    private String header;
    private byte[] body;

    public HttpResponseMessage(String header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }
}
