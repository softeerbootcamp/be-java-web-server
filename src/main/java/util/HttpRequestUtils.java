package util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestUtils {

    public static Map<String, String> parseQuerystring(String queryString) {
        Map<String, String> map = new HashMap<>();
        if ((queryString == null) || (queryString.equals(""))) {
            return map;
        }
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=", 2);
            String name = URLDecoder.decode(keyValuePair[0], StandardCharsets.UTF_8);
            if (Objects.equals(name, "")) {
                continue;
            }
            String value = keyValuePair.length > 1 ? URLDecoder.decode(
                    keyValuePair[1], StandardCharsets.UTF_8) : "";
            map.put(name, value);
        }
        return map;
    }

    public static String parseSid(String cookie) {
        String[] values = cookie.split(";");
        for (String value : values) {
            String[] keyValuePair = value.split("=", 2);
            if (keyValuePair[0].contains("sid")) {
                return keyValuePair[1];
            }
        }
        return null;
    }

}
