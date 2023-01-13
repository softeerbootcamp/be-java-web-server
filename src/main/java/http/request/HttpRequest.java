package http.request;

import http.HttpHeaders;
import http.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String ENTER = "\n";

    private final HttpStartLine startLine;
    private final HttpHeaders httpHeaders;

    private HttpRequest(HttpStartLine startLine, HttpHeaders headers) {
        this.startLine = startLine;
        this.httpHeaders = headers;
        logger.debug(httpHeaders.getHeaders().toString());
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String startLine = br.readLine();
        String extracted = extractHeaders(br);
        String[] headers = extracted.split(ENTER);

        return new HttpRequest(HttpStartLine.from(startLine), HttpHeaders.from(headers));

    }

    private static String extractHeaders(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while (Objects.nonNull(line = br.readLine()) && !line.isEmpty()) {
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

    public Uri getUri() {
        return startLine.getUri();
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders.getHeaders();
    }

}
