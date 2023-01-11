package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpUtils {
    private static Map<String, String> contentTypeMap =
            Map.of("text", "plain|html|css|js", "image", "jpeg|png|ico",
                    "audio", "mpeg|ogg|*", "video", "mp4", "application", "octet-stream",
                    "multipart", "mixed", "font", "ttf|woff");

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

    public static String setContentType(String extension) {
        for (String key : contentTypeMap.keySet()) {
            if (contentTypeMap.get(key).contains(extension))
                return key + "/" + extension;
        }
        throw new IllegalArgumentException("존재하지 않는 확장자명입니다.");
    }
}
