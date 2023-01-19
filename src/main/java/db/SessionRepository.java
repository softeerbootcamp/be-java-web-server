package db;

import model.Session;

import java.util.HashMap;
import java.util.Map;

public class SessionRepository {

    private final Map<String, Session> sessions = new HashMap<>();

    public void addSession(String id, Session session) {
        sessions.put(id, session);
    }

    public Session findById(String id) {
        return sessions.get(id);
    }

}
