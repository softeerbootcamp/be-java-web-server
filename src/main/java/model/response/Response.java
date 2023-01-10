package model.response;

import model.general.Header;
import model.general.Status;

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

    public static Response of(Status status) {
        return new Response(StatusLine.of(status), Collections.emptyMap(), null);
    }

    public static Response of(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        return new Response(statusLine, headers, body);
    }

    public void writeOutputStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(statusLine.getHttpVersion() + " " +
                statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());

        if(hasContent()) {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        }
    }

    private boolean hasContent() {
        return headers.containsKey(Header.of("Content-Length"));
    }
}
