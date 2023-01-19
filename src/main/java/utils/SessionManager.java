package utils;

import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final Map<UUID, Session> sessions = new ConcurrentHashMap<>();
    private static final long SESSION_LIFE_TIME_MS = 300000L;

    public static UUID createSession(User data) {
        UUID sessionID = UUID.randomUUID();
        sessions.put(sessionID, Session.from(data));
        return sessionID;
    }

    public static Session getData(UUID sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(UUID sessionId) {
        sessions.remove(sessionId);
    }

    public static void checkExpiredSessions() {
        long currentTime = System.currentTimeMillis();
        sessions.forEach((sessionId, data) -> {
            if (currentTime - data.getTimeStamp() > SESSION_LIFE_TIME_MS) {
                removeSession(sessionId);
            }
        });
    }
}
