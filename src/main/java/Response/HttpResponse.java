package Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StatusCode;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;
    private StatusCode status;
    private String contentType = "text/html;charset=UTF-8";
    private String protocol;

    public HttpResponse(byte[] body, StatusCode status, String contentType, String protocol) {
        this.body = body;
        this.status = status;
        this.contentType = contentType;
        this.protocol = protocol;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s \r\n", this.protocol, this.status.toString()));
        sb.append(String.format("Content-Type: %s \r\n", this.contentType));
        sb.append(String.format("Content-Length: %d \r\n", this.body==null? 0 :this.body.length));
        sb.append("\r\n");
        return sb.toString();
    }
}
