package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static utility.HttpRequestUtils.*;

public class HttpRequest {
    /*
      1. connection.inputStream으로 주어진 http requestLine parsing
      2. 요청 uri에 QueryString 포함 여부 체크
      3. uri 자원의 파일 타입에 따라 html -> templates / 이외 js, css, pont -> /static으로 라우팅
     */
    private static final String DEFAULT_PATH = "./src/main/resources";  // TODO: JVM ClassLoader 기준 경로로 변경
    private static final String TEMPLATES_PATH = "/templates";
    private static final String STATIC_PATH = "/static";

    private final StringBuilder resourcePath;

    private Map<String, String> requestHeader = new HashMap<>();
    private final BufferedReader request;

    private String methodType;
    private String path;
    private String httpVersion;
    private Map<String, String> params;

    private String body;

    public HttpRequest(InputStream request) throws IOException {
        this.params = new HashMap<>();
        this.resourcePath = new StringBuilder(DEFAULT_PATH);
        this.request = new BufferedReader(new InputStreamReader(request));

        this.parseRequestLine();
        this.parseRequestHeader();
        this.parseRequestBody();
    }

    private void parseRequestLine() throws IOException {
        String requestLine = request.readLine();

        Map<String, String> parsedRequestLine = HttpRequestUtils.parseRequestLine(requestLine);

        this.path = parsedRequestLine.get(PATH);
        this.methodType = parsedRequestLine.get(METHOD_TYPE);
        this.httpVersion = parsedRequestLine.get(HTTP_VERSION);
        String queryString = parsedRequestLine.get(QUERY_STRING);

        if(queryString != null && !queryString.isEmpty())
            parseQueryStringParams(queryString);
    }

    private void parseRequestHeader() throws IOException {
        this.requestHeader = HttpRequestUtils.parseRequestHeader(this.request);
    }

    private void parseRequestBody() throws IOException {
        String line = this.request.readLine();

        if(line == null || line.isBlank()) {
            this.body = "";
        }

        this.body = line.trim();
    }

    private void parseQueryStringParams(String queryString) {
        Map<String, String> parsedQueryString = HttpRequestUtils.parseQueryString(queryString);
        if(parsedQueryString != null)
            this.params.putAll(parsedQueryString);
    }

    public String getMethodType() {
        return methodType;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParams() {
        return new HashMap<>(this.params);
    }

    public boolean hasParams() {
        return this.params.size() > 0;
    }
}
