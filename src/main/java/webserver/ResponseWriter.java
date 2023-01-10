package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-type";
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String LINE_DELIMITER = "\r\n";
    DataOutputStream dos;

    public ResponseWriter(OutputStream out){
        this.dos =  new DataOutputStream(out);
    }

    public void write(String target) throws IOException {
        byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + target).toPath());// 현재 사용자에게 넘겨지는 응답
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    public void writeByResponse(HttpRequest request, HttpResponse response) throws IOException {
        writeHeader(response);
    }

    private void writeHeader(HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus(), LINE_DELIMITER));
        writeContentType(response);
        response.getHeaderKeys()
                .forEach(k -> writeHeaderLine(k, response.getHeaderByKey(k)));
    }

    private void writeContentType(HttpResponse response) {
        if (response.getContentType() != null) {
            writeHeaderLine(CONTENT_TYPE_HEADER_KEY, response.getContentType().getHeaderValue());
        }
    }

    private void writeHeaderLine(String headerKey, String headerValue) {
        try {
            dos.writeBytes(String.format("%s: %s%s", headerKey, headerValue, LINE_DELIMITER));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
