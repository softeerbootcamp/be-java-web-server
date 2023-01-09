package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHandlerUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandlerUtils.class);

    public static String getUrl(String line) {
        String[] tokens = line.split(" ");
        String url = tokens[1];
        logger.debug("URL : {}", url);

        return url;
    }
}
