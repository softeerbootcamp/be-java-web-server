package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private byte[] body;
    private String statusLine;
    private Map<String, String> headers;

    public HttpResponse(byte[] body) {
        this.body = body;
        initStatusLine();
        initResponse200Headers(body);
    }

    private void initStatusLine() {
        statusLine = "HTTP/1.1 200 OK ";
    }
    private void initResponse200Headers(byte[] body) {
        headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
    }
    public String response200Headers() {
        StringBuilder header = new StringBuilder();
        for (String key : headers.keySet()) {
            header.append(key+": "+headers.get(key)+"\r\n");
        }
        header.append("\r\n");
        return statusLine + "\r\n" + header.toString();
    }

    public byte[] getBody() {
        return body;
    }
}
