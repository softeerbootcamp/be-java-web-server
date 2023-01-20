package db;

import com.google.common.collect.Maps;
import model.Session;

import java.util.Map;

public class SessionRepository {

    private final Map<String, Session> sessions = Maps.newHashMap();

    public void addSession(String id, Session session) {
        sessions.put(id, session);
    }

    public Session findById(String id) {
        return sessions.get(id);
    }

}
