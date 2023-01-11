package webserver;

import enums.HttpMethod;

import java.io.IOException;
import java.util.Map;

import static util.HttpParser.REQUEST_LINE;

public class HttpRequest {
    private RequestLine requestLine;
    private final Map<String, String> headers;

    private HttpRequest(RequestLine requestLine, Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public static HttpRequest newInstance(Map<String, String> httpMessage) {
        return new HttpRequest(initRequestLine(httpMessage), extractHeadersFrom(httpMessage));
    }
    private static Map<String, String> extractHeadersFrom(Map<String, String> httpMessage) {
        httpMessage.remove(REQUEST_LINE);
        return httpMessage;
    }
    private static RequestLine initRequestLine(Map<String, String> headers) {
        String[] tokens = headers.get(REQUEST_LINE).split(" ");
        HttpMethod httpMethod = HttpMethod.getHttpMethod(tokens[0]);
        String url = tokens[1];
        String httpVersion = tokens[2];
        return new RequestLine(httpMethod, url, httpVersion);
    }
    public String getRequestUri() {
        String[] value = headers.get(REQUEST_LINE).split(" ");
        if (value[1].contains(".html") || value[1].contains(".ico")) {
            return "/templates" + value[1];
        }
        return "/static" + value[1];
    }

    public String getRequestURL() {
        String[] value = headers.get(REQUEST_LINE).split(" ");
        return value[1];
    }
}
