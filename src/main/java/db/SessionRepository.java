package db;

import exception.SessionNotFoundException;
import model.Session;

import java.util.HashMap;
import java.util.Map;

public class SessionRepository {

    private final Map<String, Session> sessions = new HashMap<>();

    public SessionRepository() {
    }

    public void addSession(String sessionId, Session session) {
        sessions.put(sessionId, session);
    }

    public Session findById(String sessionId) throws SessionNotFoundException {
        Session session = sessions.get(sessionId);
        if(session == null) {
            throw new SessionNotFoundException("session not found");
        }
        return sessions.get(sessionId);
    }
}
