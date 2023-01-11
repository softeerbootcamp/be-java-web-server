package http.response;

import http.HttpHeader;
import http.request.HttpRequest;
import utils.StatusCode;

import java.util.HashMap;

public class HttpResponse {
    private String version;
    private StatusCode statusCode;
    private HttpHeader headers;
    private HttpResponseBody body;

    public HttpResponse() {
        this.headers = HttpHeader.from(new HashMap<>());
        this.statusCode = StatusCode.OK;
        this.body = HttpResponseBody.createBody(null);
    }

    public int getContentLength() {
        if (body == null)
            return 0;
        return body.length;
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public void setBody(byte[] body) {
        this.body.setBody(body);
    }

    public String getResponseMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s \r\n", this.version, this.statusCode));
        sb.append(String.format("Content-Type: %s;charset=utf-8 \r\n", this.contentType));
        sb.append(String.format("Content-Length: %d \r\n", getContentLength()));
        if (this.statusCode == StatusCode.FOUND)
            sb.append("Location : /index.html \r\n");
        sb.append("\r\n");
        return sb.toString();
    }
}
