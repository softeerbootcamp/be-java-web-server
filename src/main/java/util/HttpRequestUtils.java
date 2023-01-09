package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class HttpRequestUtils {
    public HttpRequestUtils() {}

    public static String requestPath(String requestLine) {
        String strOfRemovedHttpMethod = requestLine.split(" ")[1];
        int idxOfLastPathChar = strOfRemovedHttpMethod.indexOf("?");
        return strOfRemovedHttpMethod.substring(0, idxOfLastPathChar);
    }

    public static Map<String, String> parseQueryString(String queryString) {
        if (Objects.isNull(queryString)) {
            return Map.of();
        }

        Map<String, String> querys = new HashMap<>();
        String url = queryString.split(" ")[1];
        int idxOfStartQueryString = url.indexOf("?") + 1;
        String[] queryparamsOfString = url.substring(idxOfStartQueryString).split("&");

        for (String queryParam: queryparamsOfString) {
            String[] queryParams = queryParam.split("=");
            querys.put(queryParams[0], queryParams[1]);
        }

        return querys;
    }

}
