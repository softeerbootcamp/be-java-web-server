package util;

import http.HttpHeader;
import http.request.HttpRequestLine;
import http.HttpUri;
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
        HttpUri uri = new HttpUri(splited[1]);
        String version = splited[2];
        return new HttpRequestLine(method, uri, version);
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

    public static String takeValueRequestParam(String[] requestParam){
        // 정보가 알맞게 들어왔는지, 빈칸은 아닌지 확인
        if(requestParam.length != 2) return null;
        return URLDecoder.decode(requestParam[1], StandardCharsets.UTF_8);
    }


}
