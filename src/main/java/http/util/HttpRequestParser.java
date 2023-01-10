package http.util;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URI;
import http.request.HttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {
    private HttpRequestParser() {}

    public static HttpRequest parse(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        try {
            String requestLine = br.readLine();
            HttpMethod method = HttpMethod.valueOf(requestLine.split(" ")[0]);
            URI uri = HttpURIParser.parse(requestLine);

            String strOfHeaders = readStrOfHeaders(br);
            HttpHeaders headers = HttpHeaderParser.parse(strOfHeaders);

            return new HttpRequest(method, uri, headers);
        } catch (IOException e) {
            throw new RuntimeException("잘못된 형식의 요청입니다.");
        }
    }

    private static String readStrOfHeaders(BufferedReader br) throws IOException {
        String line;
        StringBuilder strOfHeaders = new StringBuilder();
        while (!(line = br.readLine()).equals("")) {
            strOfHeaders.append(line).append("\n");
        }
        return strOfHeaders.toString();
    }
}
