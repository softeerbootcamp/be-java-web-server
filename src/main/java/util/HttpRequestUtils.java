package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
    public static String getUrl(String firstLine){
        String[] splited = firstLine.split(" ");
        //  /index.html : splited[1]
        String path = splited[1];
        logger.debug("Request Line에서 Path 분리하기 : {}", path);
        return path;
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
