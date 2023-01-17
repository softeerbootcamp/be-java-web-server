package http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpSession {

    private static List<String> sessions = new ArrayList<>();

    public static void addSession(String sessionId) {
        sessions.add(sessionId);
    }

    public static String parseSession(String cookie) {
        if(Objects.nonNull(cookie)) {
            return cookie.split("=")[1];
        }
        return "";
    }
    public static boolean validateSession(String sessionId) {
        return sessions.contains(sessionId);
    }
}
