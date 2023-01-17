package webserver.domain;

import enums.UserEnum;
import model.User;
import util.FileFinder;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private byte[] body = "".getBytes();
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();
    private static final String HTML = "text/html";
    private static final String CSS = "text/css";
    private static final String JS = "text/javascript";

    public String unauthorized() {
        return "HTTP/1.1 401 Unauthorized";
    }

    public String sendCookieWithRedirect(User user, String url) {
        addHeader("Set-Cookie", UserEnum.ID + "=" + user.getUserId());
        return sendRedirect(url);
    }

    public String sendRedirect(String url) {
        addHeader("Location", url);
        return createRedirectMessage();
    }

    private String createRedirectMessage() {
        statusLine = "HTTP/1.1 302 Found \r\n";
        return statusLine + processHeaders();
    }

    private String createForwardMessage() {
        statusLine = "HTTP/1.1 200 OK \r\n";
        return statusLine + processHeaders();
    }

    private String create404Message() {
        statusLine = "HTTP/1.1 404 Not Found \r\n";
        return statusLine;
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
        if (!FileFinder.isFind(uri)) {
            return create404Message();
        }
        this.body = FileFinder.findFile(uri);
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
        return createForwardMessage();
    }
}
