package util;

import http.HttpHeader;
import http.HttpRequestLine;
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

    public static HttpRequestLine getRequestLine(String firstLine) {
        logger.debug("Request Line: {}", firstLine);
        String[] splited = firstLine.split(" ");
        String method = splited[0];
        HttpUri uri = new HttpUri(splited[1]);
        String version = splited[2];
        return new HttpRequestLine(method, uri, version);
    }

    public static HttpHeader getHeaders(BufferedReader br) throws IOException {
        List<String> headers = new ArrayList<>();
        String header = br.readLine();
        while(!header.equals("")){
            headers.add(header);
            header = br.readLine();
        }
        return new HttpHeader(headers);
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> requestParamsMap = new HashMap<>();
        String[] userInputs = queryString.split("&");
        for(String userInput : userInputs){
            //logger.debug("UserInput : {}", userInput);
            String[] requestParam = userInput.split("=");
            requestParamsMap.put(requestParam[0], URLDecoder.decode(requestParam[1], StandardCharsets.UTF_8));
        }
        return requestParamsMap;
    }
}
