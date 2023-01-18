package db;

import model.Session;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionDatabase {
    private static final SessionDatabase instance;

    private static final Map<String, Session> sessionMap;

    private SessionDatabase() {}

    static {
        instance = new SessionDatabase();
        sessionMap = new HashMap<>();
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    public void add(Session session) {
        sessionMap.put(session.getSid(), session);
    }

    public Optional<Session> findById(String sid) {
        return Optional.of(sessionMap.get(sid));
    }

    public boolean existsBySessionId(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }

    public void deleteSession(String sid) {
        sessionMap.remove(sid);
    }
}
