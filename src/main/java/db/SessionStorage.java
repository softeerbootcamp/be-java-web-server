package db;

import webserver.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {
    private static Map<UUID, Session> sessions = new HashMap<>();

    public static UUID addSession(Session session) {
        UUID sid = session.getSessionId();
        sessions.put(sid, session);
        return sid;
    }
    public static Session findSessionBy(UUID uuid) {
        return sessions.get(uuid);
    }

    public static void remove(Session session) {
        if (session.getSessionId() != null) {
            sessions.remove(session.getSessionId());
        }
    }
}
