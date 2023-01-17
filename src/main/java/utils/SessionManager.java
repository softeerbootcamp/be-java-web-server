package utils;

import model.SessionData;
import model.User;

import java.util.HashMap;
import java.util.UUID;

public class SessionManager {
    private final static HashMap<UUID, SessionData> sessions = new HashMap<>();

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
}
