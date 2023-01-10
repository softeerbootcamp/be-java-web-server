package io.response;

import enums.ContentType;
import enums.LogMessage;
import enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse implements Response {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private Status status;
    private ContentType contentType;
    private byte[] body;
    private DataOutputStream out;

    public HttpResponse(FindResult findResult, DataOutputStream out) {
        this.status = findResult.getStatus();
        this.contentType = findResult.getContentType();
        this.body = findResult.getResource();
        this.out = out;
    }

    @Override
    public void send() {
        try {
            assembleMessage();
            out.flush();
        } catch (IOException e) {
            logger.error(LogMessage.SERVER_ERROR.getMessage());
        }
    }

    private void assembleMessage() {
        response200Header();
        responseBody();
    }

    private void response200Header() {
        try {
            out.writeBytes("HTTP/1.1 200 OK \r\n");
            out.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            out.writeBytes("Content-Length: " + body.length + "\r\n");
            out.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            out.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
