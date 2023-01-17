package http;

import java.util.ArrayList;
import java.util.List;

public class HttpSession {

    private static List<String> sessions = new ArrayList<>();

    public static void addSession(String sessionId) {
        sessions.add(sessionId);
    }

    public boolean validateSession(String sessionId) {
        return sessions.contains(sessionId);
    }
}
