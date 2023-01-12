package http.request;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpRequestLine httpRequestLine;
    private final HttpHeader httpHeader;

    private HttpRequest(HttpRequestLine httpRequestLine, HttpHeader httpHeader) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeader = httpHeader;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return new HttpRequest(getHttpRequestLine(br), getHttpRequestHeader(br));
    }

    private static HttpRequestLine getHttpRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        if (requestLine == null) {
            return null;
        }
        logger.debug("requestLine : {}", requestLine);

        return HttpRequestLine.from(requestLine);
    }

    private static HttpHeader getHttpRequestHeader(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while(!(line = br.readLine()).equals("")) {
            logger.debug("request header : {}", line);
            lines.add(line);
        }
        Map<String, String> requestHeader = HttpRequestUtils.parseRequestHeader(lines);

        return HttpHeader.from(requestHeader);
    }

    public String getUrl() {
        return this.httpRequestLine.getUrl();
    }

    public String getHttpVersion() {
        return this.httpRequestLine.getHttpVersion();
    }

    public String getHttpHeader(String name) {
        return httpHeader.getHeader(name);
    }
}
