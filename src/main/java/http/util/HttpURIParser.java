package http.util;

import http.common.HttpMethod;
import http.common.URI;

import java.util.HashMap;
import java.util.Map;

public class HttpURIParser {
    private HttpURIParser() {}

    public static URI parse(String requestLine) {
        return new URI(
                parseMethod(requestLine),
                parsePath(requestLine),
                parseQueryString(requestLine));
    }

    private static HttpMethod parseMethod(String requestLine) {
        return HttpMethod.valueOf(requestLine.split(" ")[0]);
    }

    private static String parsePath(String requestLine) {
        String urn = requestLine.split(" ")[1];
        int idx = urn.indexOf("?");
        if (idx == -1) {
            return urn;
        }
        return urn.substring(0, idx);
    }

    private static Map<String, String> parseQueryString(String requestLine) {
        if (!requestLine.contains("?")) {
            return Map.of();
        }

        Map<String, String> querys = new HashMap<>();
        String url = requestLine.split(" ")[1];
        int idxOfStartQueryString = url.indexOf("?") + 1;
        String[] queryparamsOfString = url.substring(idxOfStartQueryString).split("&");

        for (String queryParam: queryparamsOfString) {
            String[] queryParams = queryParam.split("=");
            querys.put(queryParams[0], queryParams[1]);
        }

        return querys;
    }
}
