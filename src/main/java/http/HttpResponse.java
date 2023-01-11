package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private DataOutputStream dos;
    private String version;
    private HttpStatusCode httpStatusCode;
    private ContentType contentType;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.version = "HTTP_1_1";
    }

    public void setResponse(String path) throws IOException {
        this.contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;
        logger.info("filePath: {}", filePath);
        byte[] body = FileIoUtils.loadFile(filePath);
        response(body);
    }

    private void response(byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 " + version + "\r\n");
        dos.writeBytes("Content-Type: " + contentType.getContentType() + "\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public OutputStream getOutputStream() {
        return new DataOutputStream(dos);
    }

    public void setStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode getStatusCode() {
        return httpStatusCode;
    }


}
