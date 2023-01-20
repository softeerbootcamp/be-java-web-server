package request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import db.SessionDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final String lineSeparator = System.lineSeparator();
    // 개행 문자 구분

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final RequestStartLine requestStartLine;
    private final RequestHeader requestHeader;
    private final RequestBody body;
    private final HttpSession httpSession;

    public HttpRequest(RequestStartLine requestStartLine, RequestHeader requestHeader, RequestBody body, HttpSession httpSession) {
        this.requestStartLine = requestStartLine;
        this.requestHeader = requestHeader;
        this.body = body;
        this.httpSession = httpSession;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        /* TODO :
            1) PR Feedback 수행 - refactoring [ RequestHeader, RequestBody 분리 ]
            2) BufferedReader  : InputStream을 읽기 위해 사용
            */

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestStartLine requestStartLine = RequestStartLine.of(bufferedReader);
        RequestHeader requestHeader = RequestHeader.of(bufferedReader);
        RequestBody requestBody = RequestBody.of(bufferedReader, requestHeader.getContentLength());
        HttpSession httpSession = getHttpSession(requestHeader);

        return new HttpRequest(requestStartLine, requestHeader, requestBody, httpSession);
    }

    private static HttpSession getHttpSession(RequestHeader requestHeader) {
        String jSessionId = requestHeader.getCookieValue("JSESSIONID");
        if (jSessionId.isEmpty()) {
            HttpSession httpSession = HttpSession.create();
            SessionDatabase.addHttpSession(httpSession);
            return httpSession;
        }
        return SessionDatabase.findHttpSessionById(jSessionId);
    }

    public Map<String, String> getParameters() {
        return requestStartLine.getParameters();
    }

    public boolean isGet() {
        return requestStartLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestStartLine.getMethod() == Method.POST;
    }

    public String getBody() {
        return body.getBody();
    }

    public String getCookieValue(String cookieName) {
        return requestHeader.getCookieValue(cookieName);
    }

    public String getDefaultPath() {
        return requestStartLine.getPath();
    }

    public String getSessionId() {
        return httpSession.getId();
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public String getMethod() {
        return requestStartLine.getMethod().getMethodName();
    }
}
