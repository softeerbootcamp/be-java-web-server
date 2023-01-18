package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class HttpResponse {
    private static final String lineSeparator = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final byte[] body;

    public HttpResponse(StatusLine statusLine,  ResponseHeader responseHeader, byte[] body) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public static HttpResponse of(String code,  ResponseHeader responseHeader, byte[] body) {
        return new HttpResponse(StatusLine.of(code), responseHeader, body);
    }

    public static HttpResponse of(String code,  ResponseHeader responseHeader) { // 리다이렉트
        return new HttpResponse(StatusLine.of(code), responseHeader, new byte[0]);
    }

    public void respond(DataOutputStream dos) {
        try {
            logger.debug("[HttpResponse] Do respond");
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(responseHeader.toValue());
            dos.writeBytes(lineSeparator);
            logger.debug("body : {}" , new String(body));
            logger.debug("body length : {}" , body.length);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void respondredirect(DataOutputStream dos) {
        try {
            logger.debug("[HttpResponse] Do respondJoin");
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(responseHeader.toValue());
            dos.writeBytes(lineSeparator);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getHeader() {
        return responseHeader.toValue();
    }

    public byte[] getBody() {
        return body;
    }
}
