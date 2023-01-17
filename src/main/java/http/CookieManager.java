package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieManager {

    public static final String COLON = ":";
    public static final String EQUALS = "=";
    public static final String COOKIE_DELIMITER = "; ";

    private final Map<String, Cookie> cookies = new HashMap<>();

    public CookieManager(String cookies) {
        if (cookies == null) return;

        String[] tokens = cookies.split(COLON);
        Arrays.stream(tokens)
                .map(String::trim)
                .map(token -> token.split(EQUALS))
                .map(value -> Cookie.of(value[0], value[1]))
                .forEach(this::addCookie);

    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    @Override
    public String toString() {
        return cookies
                .values()
                .stream()
                .map(Cookie::toString)
                .collect(Collectors.joining(COOKIE_DELIMITER));
    }

}
