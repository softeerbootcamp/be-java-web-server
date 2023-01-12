package utils;

import http.HttpHeader;
import http.request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequestGenerator {
    public static HttpRequest generateHttpMessage(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = parseStartLine(br);
        HttpHeader httpHeader = parseRequestHeader(br);
        if (httpRequestLine.hasBody()) {
            HttpRequestBody httpRequestBody = parseRequestBody(br);
            return HttpRequest.of(httpRequestLine, httpHeader, httpRequestBody);
        }
        return HttpRequest.ofNoBody(httpRequestLine, httpHeader);
    }

    private static HttpRequestLine parseStartLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] startLine = line.split(" ");
        return HttpRequestLine.of(HttpMethod.getHttpMethod(startLine[0]), URI.create(startLine[1]), startLine[2]);
    }

    private static HttpHeader parseRequestHeader(BufferedReader br) throws IOException {
        HttpHeader httpHeader = HttpHeader.from(new HashMap<>());
        while (true) {
            String line = br.readLine();
            if (line.equals("")) break;
            String[] datas = line.split(": ");
            httpHeader.addHeader(datas[0].trim(), datas[1].trim());
        }
        return httpHeader;
    }

    private static HttpRequestBody parseRequestBody(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            sb.append(line);
        }
        return HttpRequestBody.of(sb.toString());
    }
}
