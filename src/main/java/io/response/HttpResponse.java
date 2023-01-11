package io.response;

import enums.ResponseAttribute;
import enums.ResponseStartLine;
import enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String PROTOCOL = "HTTP/1.1";

    private Map<ResponseStartLine, String> startLine = new HashMap<>();
    private Map<ResponseAttribute, String> attributes = new HashMap<>();
    private byte[] body = "NOT FOUND".getBytes();
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
        startLine.put(ResponseStartLine.PROTOCOL, PROTOCOL);
        startLine.put(ResponseStartLine.STATUS, findResult.getStatus().getCode());
        startLine.put(ResponseStartLine.MESSAGE, findResult.getStatus().getMessage());
        attributes.put(ResponseAttribute.CONTENT_TYPE, findResult.getContentType().getType());
        this.body = findResult.getResource();
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

    public void setStartLine(ResponseStartLine key, String value) {
        startLine.put(key, value);
    }

    public void setAttribute(ResponseAttribute key, String value) {
        attributes.put(key, value);
    }

    private void assembleStatusLine() throws IOException {
        List<String> startLines = startLine.keySet().stream()
                .sorted(comparing(ResponseStartLine::getOrder))
                .map(key -> startLine.get(key))
                .collect(toList());
        out.writeBytes(String.join(" ", startLines) + "\n\r");
    }

    private void assembleHeader() throws IOException {
        out.writeBytes(getHeaderString());
        System.out.println(getHeaderString());
    }

    private String getHeaderString() {
        String string = "";
        Set<Map.Entry<ResponseAttribute, String>> entries = attributes.entrySet();
        for (Map.Entry<ResponseAttribute, String> entry : entries) {
            string += entry.getKey().getAttribute() + ": " + entry.getValue() + "\n\r";
        }
        return string;
    }

    private void assembleBody() throws IOException {
        out.write(body, 0, body.length);
    }

    public void setBody(byte[] resource) {
        this.body = resource;
    }

    public void redirect(String redirectUrl) {
        setStartLine(ResponseStartLine.PROTOCOL, PROTOCOL);
        setStartLine(ResponseStartLine.STATUS, Status.REDIRECT.getCode());
        setStartLine(ResponseStartLine.MESSAGE, Status.REDIRECT.getMessage());
        setAttribute(ResponseAttribute.LOCATION, redirectUrl);
        body = new byte[0];
        send();
    }
}
