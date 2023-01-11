package utils;

import http.request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequestGenerator {
    public static HttpRequest generateHttpMessage(BufferedReader br) throws IOException {
        HttpStartLine httpStartLine = parseStartLine(br);
        HttpRequestHeader httpRequestHeader = parseRequestHeader(br);
        if (httpStartLine.hasBody()) {
            HttpRequestBody httpRequestBody = parseRequestBody(br);
            return HttpRequest.of(httpStartLine, httpRequestHeader, httpRequestBody);
        }
        return HttpRequest.ofNoBody(httpStartLine, httpRequestHeader);
    }

    private static HttpStartLine parseStartLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] startLine = line.split(" ");
        return HttpStartLine.of(HttpMethod.getHttpMethod(startLine[0]), URI.create(startLine[1]), startLine[2]);
    }

    private static HttpRequestHeader parseRequestHeader(BufferedReader br) throws IOException {
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.from(new HashMap<>());
        while (true) {
            String line = br.readLine();
            if (line.equals("")) break;
            String[] datas = line.split(": ");
            httpRequestHeader.addHeader(datas[0].trim(), datas[1].trim());
        }
        return httpRequestHeader;
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
