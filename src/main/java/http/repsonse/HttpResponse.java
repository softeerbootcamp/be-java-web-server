package http.repsonse;

import utils.StatusCode;

public class HttpResponse {
    private final String version;
    private final StatusCode statusCode;
    private final String contentType;
    private final byte[] body;

    public HttpResponse(String version, StatusCode statusCode, String contentType, byte[] body) {
        this.version = version;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
    }

    public int getContentLength() {
        if (body == null)
            return 0;
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getResponseMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s \r\n", this.version, this.statusCode));
        sb.append(String.format("Content-Type: %s;charset=utf-8 \r\n", this.contentType));
        sb.append(String.format("Content-Length: %d \r\n", getContentLength()));
        if (this.statusCode == StatusCode.SEEOTHER)
            sb.append("Location : /index.html \r\n");
        sb.append("\r\n");
        return sb.toString();
    }
}
