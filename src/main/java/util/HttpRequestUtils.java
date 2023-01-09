package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
    public static String getUrl(String firstLine){
        String[] splited = firstLine.split(" ");
        //  /index.html : splited[1]
        String path = splited[1];
        logger.debug("Request Line에서 Path 분리하기 : {}", path);
        return path;
    }
}
