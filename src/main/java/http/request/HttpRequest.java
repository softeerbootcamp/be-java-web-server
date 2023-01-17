package http.request;

import db.Session;
import http.HttpHeader;
import http.exception.NullHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpRequestLine httpRequestLine;
    private HttpHeader httpHeader;
    private String body;

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) throw new NullHttpRequestException("빈 http request !!================");
        this.httpRequestLine = HttpRequestUtils.readRequestLine(line);
        this.httpHeader = HttpRequestUtils.readHeaders(br);
        if(httpRequestLine.getHttpMethod().equals("POST")) this.body = HttpRequestUtils.readBody(br, httpHeader);
    }

    public String getUri() {
        return this.httpRequestLine.getHttpUri().getUri();
    }

    public String getContentType() {
        logger.debug("Accept : {}", httpHeader.getAccept());
        return httpHeader.getAccept().split(",")[0];
    }

    public String getHttpVersion() {
        return httpRequestLine.getHttpVersion();
    }

    public boolean isPost() {
        //logger.debug("HTTP method : {}", httpRequestLine.getHttpMethod());
        return this.httpRequestLine.getHttpMethod().equals("POST");
    }

    public String getBody(){
        return this.body;
    }

    public String getFileNameExtension() {
        return httpRequestLine.getHttpUri().getFileNameExtension();
    }

    public String checkLoginStatus() {
        return Session.findUserIdBySessionId(getCookie());
        // userId가 null 이 아닌 경우 login 상태임 !
    }

    public String getCookie(){
        logger.debug("Cookie :  {}", httpHeader.getCookie());
        String cookie = httpHeader.getCookie();
        if(cookie == null) return null;
        return HttpRequestUtils.parseQueryString(httpHeader.getCookie()).get("sid");
    }

    public boolean wantDynamicHtml() {
        // TODO 동적으로 작동하는 html 리스트를 따로 빼야 할까
        return getUri().equals("/index.html") || getUri().equals("/user/list.html");
    }
}
