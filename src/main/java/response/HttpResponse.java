package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpVersion;
import util.FileIoUtils;
import webserver.ContentType;
import webserver.ContentTypeMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String lineSeparator = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    //    private final StatusCode statusCode;
    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final byte[] body;

    public HttpResponse(StatusLine statusLine, ResponseHeader responseHeader, byte[] body) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public static ResponseBuilder ok() {
        return new ResponseBuilder(StatusCode.OK);
    }
    public static ResponseBuilder found(String location) {
        return new ResponseBuilder(StatusCode.Found).location(location);
    }

    public void respond(DataOutputStream dos) { //body 담음
        try {
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(responseHeader.toValue());
            dos.writeBytes(lineSeparator);
            dos.write(body, 0, body.length);
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

    public String getCookie() {
        return responseHeader.getCookie();
    }
}
