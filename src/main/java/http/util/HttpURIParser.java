package http.util;

import http.common.URI;

import java.util.HashMap;
import java.util.Map;

public class HttpURIParser {
    private HttpURIParser() {}

    public static URI parse(String requestLine) {
        return new URI(parsePath(requestLine), parseQueryString(requestLine));
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
        String queryStrings = parseQueryStrings(requestLine);
        if (queryStrings.isBlank()) {
            return Map.of();
        }

        return createQuerys(queryStrings);
    }

    private static String parseQueryStrings(String requestLine) {
        if (!requestLine.contains("?")) {
            return "";
        }

        String url = requestLine.split(" ")[1];
        int idxOfQM = url.indexOf("?") + 1;

        return url.substring(idxOfQM);
    }

    private static Map<String, String> createQuerys(String queryStrings) {
        Map<String, String> querys = new HashMap<>();

        for (String queryParam: queryStrings.split("&")) {
            String[] split = queryParam.split("=");
            String param = "";
            if (split.length == 2) {
                param = split[1];
            }
            querys.put(split[0], param);
        }

        return querys;
    }
}
