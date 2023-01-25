package webserver.domain;

import db.SessionStorage;
import enums.UserEnum;
import model.User;
import util.FileFinder;
import was.view.ViewResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpResponse {
    public static final String FILEPATH = "./src/main/resources";
    private static final String HTML = "text/html";
    private static final String CSS = "text/css";
    private static final String JS = "text/javascript";
    private byte[] body = "".getBytes();
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();

    public String unauthorized() {
        sendRedirect("/user/login_failed.html");
        return "HTTP/1.1 302 Found\r\n" + processHeaders();
    }

    public String sendCookieWithRedirect(UUID sid, String url) {
        addHeader("Set-Cookie", "SID=" + sid + "; Path=/");
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

    public String create404Message() {
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
            return FILEPATH + "/templates" + uri;
        }
        return FILEPATH + "/static" + uri;
    }

    public String forward(String uri) {
        if (!FileFinder.isFind(uri)) {
            return create404Message();
        }
        this.body = FileFinder.findFile(uri);
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
        return createForwardMessage();
    }

    public String forward(String uri, UUID sessionId) {
        if (!FileFinder.isFind(uri)) {
            return create404Message();
        }
        this.body = ViewResolver.makeDynamicHtml(SessionStorage.findSessionBy(sessionId).getUserId(), uri);
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
        return createForwardMessage();
    }
    public String forwardListPageHeaderMessage(String userId, String uri) {
        if (!FileFinder.isFind(uri)) {
            return create404Message();
        }
        this.body = ViewResolver.makeListHtml(userId, uri);
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
        return createForwardMessage();
    }
}
