package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private byte[] body = "".getBytes();
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();
    private static final String HTML = "text/html";
    private static final String CSS = "text/css";

    public HttpResponse(byte[] body, String uri) {
        this.body = body;
        initStatusLine();
        initResponse200Headers(body.length, mime(uri));
    }

    private void initStatusLine() {
        statusLine = "HTTP/1.1 200 OK ";
    }

    private void initResponse200Headers(int bodyLength, String mime) {
        headers = new HashMap<>();
        headers.put("Content-Type", mime + ";charset=utf-8");
        headers.put("Content-Length", String.valueOf(bodyLength));
    }

    private String mime(String uri) {
        if (uri.contains("css")) {
            return CSS;
        }
        /*
        if (uri.contains("js")){
            return "text/javascript";
        }*/
        return HTML;
    }

    public String response200Headers() {
        StringBuilder header = new StringBuilder();
        for (String key : headers.keySet()) {
            header.append(key + ": " + headers.get(key) + "\r\n");
        }
        header.append("\r\n");
        return statusLine + "\r\n" + header.toString();
    }

    public byte[] getBody() {
        return body;
    }
}
