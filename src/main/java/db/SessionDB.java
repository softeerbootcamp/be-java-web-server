package db;

import com.google.common.collect.Maps;
import http.common.Session;

import java.util.Map;
import java.util.Optional;

public class SessionDB {
    private static Map<String, Session> sessions = Maps.newHashMap();

    public static void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Optional<Session> getSession(String sid) {
        return Optional.ofNullable(sessions.get(sid));
    }
}
