package db;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionDatabase {
    private static final Logger logger = LoggerFactory.getLogger(SessionDatabase.class);

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
        logger.debug("sid {} added, expires at {}", session.getSid(), session.getExpirationTime());
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
