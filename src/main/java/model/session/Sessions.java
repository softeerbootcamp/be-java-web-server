package model.session;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Sessions {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session getSession(String sessionId) {
        if(Objects.isNull(sessions.get(sessionId))) return makeSession(sessionId);

        return sessions.get(sessionId);
    }

    public static Session makeSession(String sessionId) {
        Session newSession = new Session(sessionId);
        sessions.put(sessionId, newSession);

        return newSession;
    }

    public static boolean isExistSession(String sessionId) {
        return Objects.nonNull(sessions.get(sessionId));
    }
}
