package webserver.domain;

import db.SessionStorage;
import util.FileFinder;
import was.view.LoginIndexCallback;
import was.view.ListCallback;
import was.view.ViewTemplate;

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

    public void sendCookieWithRedirect(UUID sid, String url) {
        statusLine = "HTTP/1.1 302 Found \r\n";
        addHeader("Location", url);
        addHeader("Set-Cookie", "SID=" + sid + "; Path=/");
    }

    public void sendRedirect(String url) {
        statusLine = "HTTP/1.1 302 Found \r\n";
        addHeader("Location", url);
    }

    public void notFound() {
        statusLine = "HTTP/1.1 404 Not Found \r\n";
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

    private String getHeaderToString() {
        StringBuilder header = new StringBuilder();
        for (String key : headers.keySet()) {
            header.append(key + ": " + headers.get(key) + "\r\n");
        }
        header.append("\r\n");
        return header.toString();
    }
    public String getResponseMessageHeader() {
        return statusLine + getHeaderToString();
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

    public void forward(String uri) {
        if (!FileFinder.isFind(uri)) {
            notFound();
            return;
        }
        statusLine = "HTTP/1.1 200 OK \r\n";
        this.body = FileFinder.findFile(uri);

        if (uri.contains("index.html")) {
            this.body = ViewTemplate.fileReadTemplate(null, uri, new LoginIndexCallback());
        }
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
    }

    public void forward(String uri, UUID sessionId) {
        if (!FileFinder.isFind(uri)) {
            notFound();
            return;
        }
        statusLine = "HTTP/1.1 200 OK \r\n";
        this.body = ViewTemplate.fileReadTemplate(SessionStorage.findSessionBy(sessionId).getUserId(), uri, new LoginIndexCallback());
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
    }

    public void forwardListPage(String uri, UUID sessionId) {
        if (!FileFinder.isFind(uri)) {
            notFound();
            return;
        }
        statusLine = "HTTP/1.1 200 OK \r\n";
        this.body = ViewTemplate.fileReadTemplate(SessionStorage.findSessionBy(sessionId).getUserId(), uri, new ListCallback());
        addHeader("Content-Type", mime(uri) + ";charset=utf-8");
    }
}
