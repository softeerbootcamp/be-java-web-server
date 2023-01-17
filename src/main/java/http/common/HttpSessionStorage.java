package http.common;

import http.request.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpSessionStorage {
    private HttpSessionStorage() {}

    private final static Map<String, HttpSession> db;

    static {
        db = new HashMap<>();
    }

    public static void addSession(HttpSession session) {
        db.put(session.sessionId(), session);
    }

    public static Optional<HttpSession> getSession(String sessionId) {
        if (db.containsKey(sessionId)) {
            return Optional.of(db.get(sessionId));
        }

        return Optional.empty();
    }

    public static boolean alive(HttpSession session) {
        return db.containsKey(session.sessionId());
    }

    public static boolean expire(HttpSession session) {
        db.remove(session.sessionId());
        return !db.containsKey(session.sessionId());
    }
}
