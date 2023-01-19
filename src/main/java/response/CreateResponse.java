package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpVersion;
import util.FileIoUtils;
import webserver.ContentType;
import webserver.ContentTypeMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CreateResponse {

    private static final Logger logger = LoggerFactory.getLogger(CreateResponse.class);

    private static final String DELIMITER = "\\.";

    private final StatusLine statusLine;
    private Map<String, String> fields;
    private byte[] body;

    public CreateResponse(StatusCode statusCode) {
        this.statusLine = new StatusLine(HttpVersion.ONE_POINT_ONE, statusCode);
        this.fields = new HashMap<>();
        this.body = new byte[0];
    }

    public static CreateResponse ok(){
        return new CreateResponse(StatusCode.OK);
    }

    public static CreateResponse found(String location){
        return new CreateResponse(StatusCode.Found).location(location);

    }

    public CreateResponse contentLength(int contentLength) {
        fields.put("Content-Length", String.valueOf(contentLength));
        return this;
    }

    public CreateResponse contentType(String contentType) {
        fields.put("Content-Type", contentType);
        return this;
    }

    public CreateResponse bodyByPath(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        String[] splitPath = path.split(DELIMITER);
        String extension = splitPath[splitPath.length-1];
        logger.debug("bodyPath extension : {}",extension);
        ContentType contentType = ContentTypeMapper.map(extension);

        this.contentType(contentType.value())
                .contentLength(body.length);
        this.body = body;
        return this;
    }

    public CreateResponse setCookie(String cookieName, String cookieValue, String path) {
        fields.put("Set-Cookie", String.format("%s=%s; Path=%s", cookieName, cookieValue, path));
        return this;
    }

    public CreateResponse location(String location) {
        fields.put("Location", location);
        return this;
    }
    public HttpResponse build() {
        return new HttpResponse(this.statusLine, new ResponseHeader(this.fields), this.body);
    }
}
