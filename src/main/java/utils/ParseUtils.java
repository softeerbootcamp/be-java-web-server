package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseUtils {
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(queryString.split("&")).filter(param -> !param.startsWith("="))
                .forEach(param -> {
                    String[] keyValuePair = param.split("=", 2);
                    String name = URLDecoder.decode(keyValuePair[0], StandardCharsets.UTF_8);
                    String value = keyValuePair.length > 1 ? URLDecoder.decode(keyValuePair[1], StandardCharsets.UTF_8) : "";
                    map.put(name, value);
                });
        return map;
    }
}
