package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequestUtil {

    public static final String COOKIE_DELIMITER = ";";
    public static final String EQUALS_DELIMITER = "=";
    public static final String SESSION_ID = "JSESSIONID";

    private HttpRequestUtil() {
    }

    public static Map<String, String> parseQuerystring(String queryString) {
        Map<String, String> map = new HashMap<>();
        if ((queryString == null) || (queryString.equals(""))) {
            return map;
        }
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=", 2);
            String name = URLDecoder.decode(keyValuePair[0], StandardCharsets.UTF_8);
            if (name.equals("")) {
                continue;
            }
            String value = keyValuePair.length > 1 ? URLDecoder.decode(
                    keyValuePair[1], StandardCharsets.UTF_8) : "";
            map.put(name, value);
        }
        return map;
    }

    public static String extractSessionId(String cookie) {
        String[] tokens = cookie.split(COOKIE_DELIMITER);
        return Arrays.stream(tokens)
                .map(str -> str.split(EQUALS_DELIMITER))
                .filter(strings -> SESSION_ID.equals(strings[0].trim()))
                .findFirst()
                .orElseGet(() -> new String[] {"", null})[1];
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

}
