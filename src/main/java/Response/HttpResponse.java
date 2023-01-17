package Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpResponseBody httpResponseBody;
    private HttpResponseStartLine httpResponseStartLine;
    private HttpResponseHeaders httpResponseHeaders;
    public static final String NEWLINE = "\r\n";

    public HttpResponse() {

    }

    public HttpResponse startLine(HttpResponseStartLine httpResponseStartLine) {
        this.httpResponseStartLine = httpResponseStartLine;
        return this;
    }

    public HttpResponse headers(HttpResponseHeaders httpResponseHeaders) {
        this.httpResponseHeaders = httpResponseHeaders;
        return this;
    }

    public HttpResponse body(HttpResponseBody httpResponseBody) {
        this.httpResponseBody = httpResponseBody;
        return this;
    }

    public void putHeader(String key, String value) {
        this.httpResponseHeaders.putHeader(key, value);
    }

    @Override
    public String toString() {
        return httpResponseStartLine.toString() + httpResponseHeaders.toString();
    }

    public byte[] getBody() {
        return this.httpResponseBody.getBody();
    }
}
