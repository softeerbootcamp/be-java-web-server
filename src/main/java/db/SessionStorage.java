package db;

import model.User;
import util.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {
    private static Map<UUID, Session> sessions = new HashMap<>();

    public static UUID addSession(Session session) {
        UUID sid = session.getSid();
        sessions.put(sid, session);
        return sid;
    }
    public static Session findSessionBy(UUID uuid) {
        return sessions.get(uuid);
    }

    public static void remove(Session session) {
        if (session.getSid() != null) {
            sessions.remove(session.getSid());
        }
    }
}
