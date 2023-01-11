package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private DataOutputStream dos;
    private String version;
    private HttpStatusCode httpStatusCode;
    private ContentType contentType;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.version = "HTTP/1.1";
    }

    public void setResponse(String path) throws IOException {
        this.contentType = ContentType.from(path);
        this.httpStatusCode = HttpStatusCode.OK;
        String filePath = contentType.getDirectory() + path;
        logger.info("filePath: {}", filePath);
        byte[] body = FileIoUtils.loadFile(filePath);
        logger.info("body: {}", body.length);
        response(body);
    }

    private void response(byte[] body) throws IOException {
        dos.writeBytes(version + httpStatusCode + "\r\n");
        dos.writeBytes("Content-Type: " + contentType.getContentType() + "\r\n");
        logger.info("Content-정Type: " + contentType.getContentType());
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
