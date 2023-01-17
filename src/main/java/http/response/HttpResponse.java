package http.response;

import http.HttpHeader;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.HttpResponseUtils;

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
    private String destination;

    private HttpResponse(HttpResponseBuilder httpResponseBuilder){
        this.statusLine = httpResponseBuilder.httpStatusLine;
        this.body = httpResponseBuilder.body;
        this.contentType = httpResponseBuilder.contentType;
        this.destination = httpResponseBuilder.destination;
        this.header = httpResponseBuilder.header;
    }

    public static class HttpResponseBuilder{
        private HttpStatusLine httpStatusLine;
        private byte[] body;
        private String contentType;
        private String destination;
        private HttpHeader header;

        public HttpResponseBuilder(){}

        public HttpResponseBuilder setHttpStatusLine(HttpStatusLine httpStatusLine){
            this.httpStatusLine = httpStatusLine;
            return this;
        }

        public HttpResponseBuilder makeHeader() {
            if (this.httpStatusLine.checkStatus(HttpStatus.OK)) {
                this.header = HttpResponseUtils.makeResponse200Header(contentType, body.length);
                return this;
            }
            if (this.httpStatusLine.checkStatus(HttpStatus.FOUND)) {
                this.header = HttpResponseUtils.makeResponse302Header(destination);
                return this;
            }
            // 200 302 응답 둘다 아니면?
            return null;
        }

        public HttpResponseBuilder setBody(byte[] body){
            this.body = body;
            return this;
        }

        public HttpResponseBuilder setContentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public HttpResponseBuilder setDestination(String destination){
            this.destination = destination;
            return this;
        }

        public HttpResponseBuilder addCookie(String cookieValue){
            this.header.addHeader("Set-Cookie", cookieValue);
            return this;
        }

        public HttpResponse build(){
            return new HttpResponse(this);
        }

    }

    public void send(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseHeader(DataOutputStream dos) {
        try {
            logger.debug("send : {}", statusLine.toStringForResponse());
            logger.debug("send : {}", header.toString());
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
