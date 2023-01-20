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

    private final Map<String, Session> sessions;

    private SessionDatabase() {
        sessions = new HashMap<>();
    }

    static {
        instance = new SessionDatabase();
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    public void add(Session session) {
        logger.debug("sid {} added, expires at {}", session.getSid(), session.getExpirationTime());
        sessions.put(session.getSid(), session);
    }

    public Optional<Session> findById(String sid) {
        return Optional.ofNullable(sessions.get(sid));
    }

    public boolean existsBySessionId(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public void deleteSession(String sid) {
        sessions.remove(sid);
    }
}
