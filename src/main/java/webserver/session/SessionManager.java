package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "sid";

    private static final Map<String, String> sessionStore = new ConcurrentHashMap<>();

    public static String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, userId);
        return sessionId;
    }
}
