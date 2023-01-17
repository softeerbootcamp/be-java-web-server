package http;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static final Map<String, Session> sessionStore = new HashMap<>();

    public static Session getSession(String id) {
        return sessionStore.get(id);
    }

    public static void addSession(Session session) {
        sessionStore.put(session.getId(), session);
    }

    public static Session create() {
        Session session = new Session();
        addSession(session);

        return session;
    }

    public static boolean isContains(String key) {
        return sessionStore.containsKey(key);
    }
}
