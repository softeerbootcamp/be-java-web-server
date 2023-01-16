package http.response;

import http.HttpHeader;
import http.HttpStatus;
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

    public HttpResponse(HttpResponseBuilder httpResponseBuilder){
        this.statusLine = httpResponseBuilder.httpStatusLine;
        this.body = httpResponseBuilder.body;
        this.contentType = httpResponseBuilder.contentType;

        this.header = makeHeader();
    }

    private HttpHeader makeHeader() {
        if (statusLine.checkStatus(HttpStatus.OK)) return makeResponse200Header();
        if (statusLine.checkStatus(HttpStatus.FOUND)) return makeResponse302Header();

        // 200 302 응답 둘다 아니면?
        return null;
    }

    public static class HttpResponseBuilder{
        private HttpStatusLine httpStatusLine;
        private HttpHeader httpHeader;
        private byte[] body;
        private String contentType;

        public HttpResponseBuilder(){}

        public HttpResponseBuilder setHttpStatusLine(HttpStatusLine httpStatusLine){
            this.httpStatusLine = httpStatusLine;
            return this;
        }

        public HttpResponseBuilder setBody(byte[] body){
            this.body = body;
            return this;
        }

        public HttpResponseBuilder setContentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public HttpResponse build(){
            return new HttpResponse(this);
        }

    }

    private HttpHeader makeResponse200Header() {
        List<String> headerLines = new ArrayList<>();
        logger.debug("contentType: {}", contentType);
        headerLines.add("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
        headerLines.add("Content-Length: " + body.length + System.lineSeparator());
        return new HttpHeader(headerLines);
    }

    private HttpHeader makeResponse302Header() {
        List<String> headerLines = new ArrayList<>();
        // TODO 회원 가입에 대한 리다이렉트로 첫 페이지 띄워주게 하드 코딩 했는데 나중에 확장 해야 할듯
        headerLines.add("Location: " + "/index.html" + System.lineSeparator());
        return new HttpHeader(headerLines);
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
