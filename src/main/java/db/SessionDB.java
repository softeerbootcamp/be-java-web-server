package db;

import com.google.common.collect.Maps;
import http.common.Session;

import java.util.Map;

public class SessionDB {
    private static Map<String, Session> sessions = Maps.newHashMap();

    public static void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Session getSession(String sid) {
        return sessions.get(sid);
    }
}
