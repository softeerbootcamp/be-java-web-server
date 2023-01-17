package utils;

import model.SessionData;
import model.User;

import java.util.HashMap;
import java.util.UUID;

public class SessionManager {
    private static final HashMap<UUID, SessionData> sessions = new HashMap<>();
    private static final long SESSION_LIFE_TIME = 300000L;

    public static UUID createSession(User data) {
        UUID sessionID = UUID.randomUUID();
        sessions.put(sessionID, SessionData.from(data));
        return sessionID;
    }

    public static SessionData getData(UUID sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(UUID sessionId) {
        sessions.remove(sessionId);
    }

    public static void checkExpiredSessions() {
        long currentTime = System.currentTimeMillis();
        sessions.forEach((sessionId, data) -> {
            if (currentTime - data.getTimeStamp() > SESSION_LIFE_TIME) {
                removeSession(sessionId);
            }
        });
    }
}
