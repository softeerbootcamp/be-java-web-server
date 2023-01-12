package io.response;

import enums.Status;
import io.request.Header;
import io.response.startline.RequestStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

import static io.response.ResponseAttribute.*;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private RequestStartLine startLine = new RequestStartLine();
    private Header header = new Header();
    private Body body = new Body();
    private DataOutputStream out;

    public HttpResponse(DataOutputStream out) {
        this.out = out;
    }

    public void send() {
        try {
            assembleMessage();
            out.flush();
        } catch (IOException e) {
            logger.error("server error");
        }
    }

    public void update(FindResult findResult) {
        startLine.setStatus(findResult.getStatus());
        header.setAttribute(CONTENT_TYPE, findResult.getContentType().getType());
        header.setAttribute(CONTENT_LENGTH, findResult.getResource().length);
        body.setBody(findResult.getResource());
    }

    private void assembleMessage() {
        try {
            assembleStatusLine();
            assembleHeader();
            assembleBody();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void assembleStatusLine() throws IOException {
        out.writeBytes(startLine.toString());
    }

    private void assembleHeader() throws IOException {
        out.writeBytes(header.toString());
    }

    private void assembleBody() throws IOException {
        out.write(body.getBody(), 0, body.getBody().length);
    }

    public void redirect(String redirectUrl) {
        startLine.setStatus(Status.REDIRECT);
        header.setAttribute(LOCATION, redirectUrl);
        body.clear();
        send();
    }
}
