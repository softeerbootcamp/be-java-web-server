package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;


public class Response {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String INDEX_FILE = "/";
    public static final String INDEX_HTML = "/index.html";
    public static final String TEMPLATE_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";
    public static final String HTML_SUFFIX = ".html";
    public static final String ICO_SUFFIX = ".ico";

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final String path;
    private final Status status;
    private final String redirect;

    private Response(String path, Status status, String redirect) {
        this.path = path;
        this.status = status;
        this.redirect = redirect;
    }

    public void flush(DataOutputStream dataOutputStream) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getPath());
        header(dataOutputStream, body.length);
        body(dataOutputStream, body);
    }

    private void header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            redirectIfFound(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void body(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectIfFound(DataOutputStream outputStream) throws IOException {
        if (status.equals(Status.FOUND)) {
            outputStream.writeBytes("Location: " + BASE_URL + redirect);
        }
    }

    public String getPath() {
        if (!redirect.isEmpty()) {
            return TEMPLATE_PREFIX + redirect;
        }
        if (INDEX_FILE.equals(path)) {
            return TEMPLATE_PREFIX + INDEX_HTML;
        }
        if (!path.contains(".") || path.endsWith(HTML_SUFFIX) || path.endsWith(ICO_SUFFIX)) {
            return TEMPLATE_PREFIX + path;
        }
        return STATIC_PREFIX + path;
    }
}
