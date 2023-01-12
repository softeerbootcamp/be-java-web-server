package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final HttpStatusLine statusLine;
    private HttpHeader header;
    private byte[] body;

    private String contentType;


    public HttpResponse(HttpStatusLine statusLine, byte[] body, String contentType) {
        this.statusLine = statusLine;
        this.body = body;
        this.contentType = contentType;

        // Status에 따른 Header 생성
        if (statusLine.checkStatus(HttpStatus.OK)) makeResponse200Header();
        if (statusLine.checkStatus(HttpStatus.FOUND)) makeResponse302Header();
    }

    private void makeResponse200Header() {
        List<String> headerLines = new ArrayList<>();
        logger.debug("contentType: {}", contentType);
        headerLines.add("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
        headerLines.add("Content-Length: " + body.length + System.lineSeparator());
        this.header = new HttpHeader(headerLines);
    }

    private void makeResponse302Header() {
        List<String> headerLines = new ArrayList<>();
        // TODO 회원 가입에 대한 리다이렉트로 첫 페이지 띄워주게 하드 코딩 했는데 나중에 확장 해야 할듯
        headerLines.add("Location: " + "/index.html" + System.lineSeparator());
        this.header = new HttpHeader(headerLines);
    }

    public void send(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseHeader(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.toStringForResponse());
            dos.writeBytes(header.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            if(body != null) dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
