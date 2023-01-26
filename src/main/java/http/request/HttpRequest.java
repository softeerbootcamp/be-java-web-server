package http.request;

import http.HttpHeader;
import utils.enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine startLine;
    private final HttpHeader httpHeader;
    private final HttpRequestBody requestBody;

    private HttpRequest(HttpRequestLine startLine, HttpHeader requestHeader, HttpRequestBody requestBody) {
        this.startLine = startLine;
        this.httpHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = createStartLine(br);
        HttpHeader httpHeader = createRequestHeader(br);
        if (httpRequestLine.hasBody()) {
            HttpRequestBody httpRequestBody = createRequestBody(httpHeader.getContentLength(), br);
            return new HttpRequest(httpRequestLine, httpHeader, httpRequestBody);
        }
        return new HttpRequest(httpRequestLine, httpHeader, null);
    }

    private static HttpRequestLine createStartLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] startLine = line.split(" ");
        return HttpRequestLine.of(HttpMethod.getHttpMethod(startLine[0]), URI.create(startLine[1]), startLine[2]);
    }

    private static HttpHeader createRequestHeader(BufferedReader br) throws IOException {
        HttpHeader httpHeader = HttpHeader.create();
        while (true) {
            String line = br.readLine();
            if (line.equals("")) break;
            String[] datas = line.split(":");
            httpHeader.addHeader(datas[0].trim(), datas[1].trim());
        }
        return httpHeader;
    }

    private static HttpRequestBody createRequestBody(int contentLength, BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (contentLength != 0) {
            sb.append((char) br.read());
            contentLength -= 1;
        }
        return HttpRequestBody.from(sb.toString());
    }

    public Map<String, String> getRequestBody() {
        return requestBody.getParams();
    }

    public HttpMethod getHttpMethod() {
        return this.startLine.getMethod();
    }

    public String getHeaderValue(String key) {
        return this.httpHeader.getHeaders().get(key);
    }

    public URI getUri() {
        return this.startLine.getUri();
    }

    public String getVersion() {
        return this.startLine.getVersion();
    }

    public Map<String, String> getQueryParams() {
        return this.getUri().getParams();
    }

    public String getSession() {
        String cookie = getHeaderValue("Cookie");
        if (!hasSession(cookie))
            return null;
        String[] cookies = cookie.split(";");
        String sid = Arrays.stream(cookies).filter(c -> c.contains("sid")).findFirst().orElse("");
        return sid.split("=")[1];
    }

    private boolean hasSession(String cookie) {
        return cookie != null && cookie.contains("sid");
    }
}
