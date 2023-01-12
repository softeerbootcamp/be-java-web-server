package webserver.domain;

import util.FileFinder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static util.HttpParser.REQUEST_LINE;

public class HttpResponse {

    private byte[] body = "".getBytes();
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();
    private static final String HTML = "text/html";
    private static final String CSS = "text/css";
    private static final String JS = "text/javascript";

    public HttpResponse() {
    }

    public String sendRedirect(String url) {
        addHeader("Location", url);
        return createRedirectMessage();
    }

    public String createRedirectMessage() {
        statusLine = "HTTP/1.1 302 Found \r\n";
        return statusLine + processHeaders();
    }

    public String createForwardMessage() {
        statusLine = "HTTP/1.1 200 OK \r\n";
        return statusLine + processHeaders();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private String mime(String uri) {
        if (uri.contains("css")) {
            return CSS;
        }
        if (uri.contains("js")) {
            return JS;
        }
        return HTML;
    }

    private String processHeaders() {
        StringBuilder header = new StringBuilder();
        for (String key : headers.keySet()) {
            header.append(key + ": " + headers.get(key) + "\r\n");
        }
        header.append("\r\n");
        return header.toString();
    }

    public String response200Headers() {
        StringBuilder header = new StringBuilder();
        for (String key : headers.keySet()) {
            header.append(key + ": " + headers.get(key) + "\r\n");
        }
        header.append("\r\n");
        return statusLine + header.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public String findPath(String uri) {
        if (uri.contains(".html") || uri.contains(".ico")) {
            return "/templates" + uri;
        }
        return "/static" + uri;
    }

    public String forward(String uri) {
        this.body = FileFinder.findFile(uri);
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
        return createForwardMessage();
    }
}
