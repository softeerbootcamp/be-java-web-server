package http.request;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpRequestLine httpRequestLine;
    private HttpHeader httpHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) return;
        this.httpRequestLine = HttpRequestUtils.getRequestLine(line);
        this.httpHeader = HttpRequestUtils.getHeaders(br);
    }

    public String getUri() {
        return this.httpRequestLine.getHttpUri().getUri();
    }

    public boolean wantStatic() {
        return httpRequestLine.getHttpUri().isStaticUri();
    }

    public boolean wantDynamic() {
        return httpRequestLine.getHttpUri().isDynamicUri();
    }

    public String getContentType() {
        //logger.debug("Accept : {}", httpHeader.getAccept());
        return httpHeader.getAccept().split(",")[0];
    }

    public String getHttpVersion() {
        return httpRequestLine.getHttpVersion();
    }
}
