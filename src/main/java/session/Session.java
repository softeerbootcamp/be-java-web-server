package session;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static final Map<String, User> session;

    static {
        session = new HashMap<>();
    }

    public static void add(String sessionId, User user) {
        session.put(sessionId, user);
    }

    public static boolean existsBySessionId(String sessionId) {
        return session.containsKey(sessionId);
    }
}
