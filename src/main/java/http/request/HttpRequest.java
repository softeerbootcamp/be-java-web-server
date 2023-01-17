package http.request;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String ENTER = "\n";

    private final HttpStartLine startLine;
    private final HttpHeader httpHeaders;
    private final HttpRequestBody requestBody;
    private final CookieManager cookies;

    private HttpRequest(
            HttpStartLine startLine,
            HttpHeader headers,
            HttpRequestBody requestBody,
            CookieManager cookies
    ) {
        this.startLine = startLine;
        this.httpHeaders = headers;
        this.requestBody = requestBody;
        this.cookies = cookies;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String startLine = br.readLine();
        String extracted = extractHeaders(br);
        String[] headers = extracted.split(ENTER);

        return of(HttpStartLine.from(startLine), HttpHeader.from(headers), br);
    }

    public static HttpRequest of(
            HttpStartLine startLine,
            HttpHeader headers,
            BufferedReader br
    ) throws IOException {
        HttpMethod httpMethod = startLine.getMethod();
        CookieManager cookies = new CookieManager(headers.getHeaders().get("Cookie"));
        logger.info("HttpStartLine: {} {} {}", startLine.getMethod(), startLine.getUri().getPath(), startLine.getHttpVersion());
        logger.debug("Headers: {}", headers);

        if (httpMethod.equals(HttpMethod.GET)) {
            HttpRequestBody httpRequestBody = HttpRequestBody.createEmptyRequestBody();
            return new HttpRequest(startLine, headers, httpRequestBody, cookies);
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            int contentLength = Integer.parseInt(headers.getValue("Content-Length"));
            String queryString = readData(br, contentLength);
            HttpRequestBody httpRequestBody = HttpRequestBody.createRequestBody(queryString);
            return new HttpRequest(startLine, headers, httpRequestBody, cookies);
        }

        return null;
    }

    private static String extractHeaders(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public HttpStartLine getStartLine() {
        return startLine;
    }

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public Map<String, String> getParameters() {
        return requestBody.getParameters();
    }

    public Uri getUri() {
        return startLine.getUri();
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders.getHeaders();
    }

    public CookieManager getCookies() {
        return cookies;
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

}
