package Response;

import Request.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpResponseBody httpResponseBody;
    private HttpResponseStartLine httpResponseStartLine;
    private HttpResponseHeaders httpResponseHeaders;
    public static final String NEWLINE = "\r\n";

    public HttpResponse(HttpResponseStartLine httpResponseStartLine, HttpResponseBody httpResponseBody, HttpResponseHeaders httpResponseHeaders) {
        this.httpResponseBody = httpResponseBody;
        this.httpResponseStartLine = httpResponseStartLine;
        this.httpResponseHeaders = httpResponseHeaders;
    }

    public static HttpResponse createResponse(String path, StatusCode code, String protocol) {
        HttpResponseStartLine httpResponseStartLine = new HttpResponseStartLine(code, protocol);
        HttpResponseBody httpResponseBody = HttpResponseBody.from(path);
        HttpResponseHeaders httpResponseHeaders = HttpResponseHeaders.of(path, httpResponseBody.getLength());
        return new HttpResponse(httpResponseStartLine, httpResponseBody, httpResponseHeaders);
    }

    public void putHeader(String key, String value) {
        this.httpResponseHeaders.putHeader(key, value);
    }

    @Override
    public String toString() {
        return httpResponseStartLine.toString() + httpResponseHeaders.toString();
    }
    public void setHttpResponseBody(byte[] body) {
        this.httpResponseBody.setBody(body);
    }
    public byte[] getBody() {
        return this.httpResponseBody.getBody();
    }
    public StatusCode getStatusCode() {
        return this.httpResponseStartLine.getStatus();
    }
    public Map<String, String> getHeader() {
        return this.httpResponseHeaders.getHeaders();
    }
}
