package db;

import com.google.common.collect.Maps;
import model.HttpCookie;
import model.User;

import java.util.*;
import java.util.stream.Collectors;

public class CookieDataBase {

    private static Map<String, HttpCookie> cookies = Maps.newHashMap();

    public static void addCookie(HttpCookie cookie) {
        cookies.put(cookie.getSessionId(), cookie);
    }

    public static Optional<HttpCookie> findCookieById(String sessionId) {
        return Optional.ofNullable(cookies.get(sessionId));
    }

    public static void deleteCookie(String sessionId){
        cookies.remove(sessionId);
    }

    public static ArrayList<HttpCookie> findAll() {
        return cookies.values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
