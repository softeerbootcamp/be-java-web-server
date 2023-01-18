package http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionRepository {
    private static final Map<String, Session> sessions = new HashMap<>();

    public static void addSession(String id, Session session) {
        sessions.put(id, session);
    }

    public static Session findById(String id) {
        return sessions.get(id);
    }

}
