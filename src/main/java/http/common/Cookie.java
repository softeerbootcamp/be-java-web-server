package http.common;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Cookie {

    private static final String ROOT_PATH = "/";
    private String key;
    private String value;
    private String path;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
        this.path = ROOT_PATH;
    }

    public static List<Cookie> cookify(String cookieString) {
        List<Cookie> cookies = Lists.newArrayList();
        Arrays.stream(cookieString.split(",")).forEach(cookieChunk -> {
            if (!cookieChunk.isEmpty()) {
                String[] cookiePair = cookieChunk.split("=");
                String key = cookiePair[0];
                String value = cookiePair[1];
                cookies.add(new Cookie(key, value));
            }
        });
        return cookies;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value + "; " + "path=" + path;
    }
}
