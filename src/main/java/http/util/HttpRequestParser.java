package http.util;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URL;
import http.request.HttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static http.util.HttpRequestReader.*;
public class HttpRequestParser {
    private HttpRequestParser() {}

    public static HttpRequest parse(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        try {
            String readRequestLine = readRequestLine(br);
            HttpMethod method = HttpMethod.valueOf(readRequestLine.split(" ")[0]);
            URL url = HttpURIParser.parse(readRequestLine);
            HttpHeaders headers = HttpHeaderParser.parse(readHeader(br));
            Map<String, String> data = HttpFormBodyParser.parse(readBody(br, headers.contentLength()));

            return new HttpRequest(method, url, headers, data);
        } catch (IOException e) {
            throw new RuntimeException("잘못된 형식의 요청입니다.");
        }
    }
}
