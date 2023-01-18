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

import static utils.HttpRequestUtil.extractSessionId;
import static utils.HttpRequestUtil.readData;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String ENTER = "\n";

    private final HttpStartLine startLine;
    private final HttpHeader httpHeaders;
    private final HttpRequestBody requestBody;

    private HttpRequest(
            HttpStartLine startLine,
            HttpHeader headers,
            HttpRequestBody requestBody
    ) {
        this.startLine = startLine;
        this.httpHeaders = headers;
        this.requestBody = requestBody;
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
        logger.info("HttpStartLine: {} {} {}", startLine.getMethod(), startLine.getUri().getPath(), startLine.getHttpVersion());
        logger.debug("Headers: {}", headers);

        if (httpMethod.equals(HttpMethod.GET)) {
            HttpRequestBody httpRequestBody = HttpRequestBody.createEmptyRequestBody();
            return new HttpRequest(startLine, headers, httpRequestBody);
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            int contentLength = Integer.parseInt(headers.getValue("Content-Length"));
            String queryString = readData(br, contentLength);
            HttpRequestBody httpRequestBody = HttpRequestBody.createRequestBody(queryString);
            return new HttpRequest(startLine, headers, httpRequestBody);
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

    public Map<String, String> getParameters() {
        return requestBody.getParameters();
    }

    public String getParameter(String key) {
        return getParameters().get(key);
    }

    public Uri getUri() {
        return startLine.getUri();
    }

    public String getPath() {
        return getUri().getPath();
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders.getHeaders();
    }

    public String getSessionId() {
        String cookies = httpHeaders.getValue("Cookie");
        if (cookies == null)
            return null;

        return extractSessionId(cookies);

    }

}
