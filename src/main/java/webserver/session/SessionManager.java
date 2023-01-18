package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> sessionStore = new ConcurrentHashMap<>();

    public static String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();

        Session session = Session.of(sessionId, userId);
        sessionStore.put(sessionId, session);

        return sessionId;
    }

    public static boolean hasSession(String sessionId) {
        return sessionStore.containsKey(sessionId);
    }
}
