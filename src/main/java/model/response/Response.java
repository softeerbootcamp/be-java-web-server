package model.response;

import model.general.Header;
import model.general.Status;
import model.request.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class Response {
    private final StatusLine statusLine;
    private final Map<Header, String> headers;
    private final byte[] body;

    private Response(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static Response of(Request request, Status status) {
        return new Response(StatusLine.of(request.getRequestLine().getHttpVersion(), status),
                Collections.emptyMap(), null);
    }

    public static Response of(StatusLine statusLine, Map<Header, String> headers) {
        return new Response(statusLine, headers, null);
    }

    public static Response of(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        return new Response(statusLine, headers, body);
    }

    public void writeOutputStream(DataOutputStream dataOutputStream) throws IOException {
        writeStatusLine(dataOutputStream);
        writeResponseHeader(dataOutputStream);
        writeResponseBody(dataOutputStream);
    }

    private void writeStatusLine(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(statusLine.getStatusLine() + System.lineSeparator());
    }

    private void writeResponseHeader(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<Header, String> headerEntry : headers.entrySet()) {
            dataOutputStream.writeBytes(headerEntry.getKey().getHeader() + ": " + headerEntry.getValue() +
                    System.lineSeparator());
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    private void writeResponseBody(DataOutputStream dataOutputStream) throws IOException {
        if(hasContent()) {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        }
    }

    private boolean hasContent() {
        return headers.containsKey(Header.from("Content-Length"));
    }
}
