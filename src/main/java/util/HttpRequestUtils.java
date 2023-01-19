package util;

import http.HttpHeader;
import http.HttpUri;
import http.request.HttpRequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static HttpRequestLine readRequestLine(String firstLine) {
        logger.debug("Request Line: {}", firstLine);
        String[] splited = firstLine.split(" ");
        String method = splited[0];
        HttpUri uri = new HttpUri(checkUriNothing(splited[1]));
        String version = splited[2];
        return new HttpRequestLine(method, uri, version);
    }

    private static String checkUriNothing(String uri) {
        // http://localhost:8080/ input 들어올 경우 uri는 "/"
        if (uri.equals("/")) return "/index.html";
        return uri;
    }

    public static HttpHeader readHeaders(BufferedReader br) throws IOException {
        List<String> headers = new ArrayList<>();
        String header = br.readLine();
        while (!header.equals("")) {
            headers.add(header);
            header = br.readLine();
        }
        return new HttpHeader(headers);
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> requestParamsMap = new HashMap<>();
        String[] userInputs = queryString.split("&");
        for (String userInput : userInputs) {
            String[] requestParam = userInput.split("=");
            String requestParamValue = takeValueRequestParam(requestParam);
            requestParamsMap.put(requestParam[0], requestParamValue);
        }
        return requestParamsMap;
    }

    public static String takeValueRequestParam(String[] requestParam) {
        // 정보가 알맞게 들어왔는지, 빈칸은 아닌지 확인
        if (requestParam.length != 2) return null;
        return URLDecoder.decode(requestParam[1], StandardCharsets.UTF_8);
    }


    public static String readBody(BufferedReader br, HttpHeader header) throws IOException {
        String requestBody = readData(br, Integer.parseInt(header.getContentLength()));
        logger.debug("Request Body : {}", requestBody);
        return requestBody;
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
