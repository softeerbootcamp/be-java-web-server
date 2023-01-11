package util;

import http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static HttpMethod getHttpMethod(String requestLine) {
        String method = requestLine.split(" ")[0];
        logger.debug("HTTP Method : {}", method);

        return HttpMethod.valueOf(method);
    }

    public static String getUrl(String requestLine) {
        String url = requestLine.split(" ")[1];
        logger.debug("URL : {}", url);

        return url;
    }

    public static String getHttpVersion(String requestLine) {
        String version = requestLine.split(" ")[2];
        logger.debug("HTTP Version : {}", version);

        return version;
    }

    public static Map<String, String> parseRequestHeader(List<String> lines) {
        Map<String, String> requestHeader = new HashMap<>();
        for (String line : lines) {
            String[] header = line.split(": ");
            requestHeader.put(header[0], header[1]);
        }

        return requestHeader;
    }

    public static String getQueryString(String url) {
        String[] splited = url.split("\\?");
        String queryString = splited[1];
        logger.debug("queryString : {}" , queryString);

        return queryString;
    }

    public static Map<String, String> parseQueryString(String query) {
        Map<String, String> result = new HashMap<>();

        for (String parameter : query.split("&")) {
            String[] info = parameter.split("=");
            result.put(info[0], decode(info[1]));
        }

        return result;
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
