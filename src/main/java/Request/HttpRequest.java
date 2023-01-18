package Request;

import java.io.BufferedReader;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestParams httpRequestParams;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestStartLine httpRequestStartLine;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpRequestParams httpRequestParams, HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestParams = httpRequestParams;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestStartLine = httpRequestStartLine;
    }

    public static HttpRequest createReqeust(BufferedReader br) {
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.from(br);
        return httpRequestStartLine.getMethod().getRequestByMethod(br, httpRequestStartLine);
    }

    public Map<String,String> getHttpRequestHeaders() {
        return httpRequestHeaders.getHeaders();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getProtocol() {
        return httpRequestStartLine.getProtocol();
    }

    public Map<String, String> getParams() {
        return httpRequestParams.getParams();
    }
}
