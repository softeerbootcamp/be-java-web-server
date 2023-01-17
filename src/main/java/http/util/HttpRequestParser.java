package http.util;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URL;
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

    private static String readRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static String readHeader(BufferedReader br) throws IOException {
        String line;
        StringBuilder strOfHeaders = new StringBuilder();
        while (!(line = br.readLine()).equals("")) {
            strOfHeaders.append(line).append("\n");
        }
        return strOfHeaders.toString();
    }

    private static String readBody(BufferedReader br, int contentLength) throws IOException {
        char[] data = new char[contentLength];
        int read = br.read(data);
        if (read == -1) {
            throw new BadRequestException("요청 형식이 잘못되었습니다.");
        }
        return String.valueOf(data);
    }
}
