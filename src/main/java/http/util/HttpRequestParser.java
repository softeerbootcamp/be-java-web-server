package http.util;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URI;
import http.exception.BadRequestException;
import http.request.HttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

            String strOfBody = "";
            if (hasBody(headers)) {
                strOfBody = readStrOfBody(br, Integer.parseInt(headers.getValue("Content-Length")));
            }
            Map<String, String> data = HttpBodyParser.parse(strOfBody);

            return new HttpRequest(method, uri, headers, data);
        } catch (IOException e) {
            throw new RuntimeException("잘못된 형식의 요청입니다.");
        }
    }

    private static boolean hasBody(HttpHeaders headers) {
        return headers.keys().contains("Content-Type") || headers.keys().contains("Content-Length");
    }

    private static String readStrOfHeaders(BufferedReader br) throws IOException {
        String line;
        StringBuilder strOfHeaders = new StringBuilder();
        while (!(line = br.readLine()).equals("")) {
            strOfHeaders.append(line).append("\n");
        }
        return strOfHeaders.toString();
    }

    private static String readStrOfBody(BufferedReader br, int contentLength) throws IOException {
        char[] data = new char[contentLength];
        int read = br.read(data);
        if (read == -1) {
            throw new BadRequestException("요청 형식이 잘못되었습니다.");
        }
        return String.valueOf(data);
    }
}
