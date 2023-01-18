package service;

import db.SessionRepository;
import model.Session;

import java.util.UUID;

public class SessionService {

    public String makeSessionId() {
        return UUID.randomUUID().toString();
    }

    public void makeSession(String sessionId, String value) {
        Session session = new Session(sessionId);
        session.setAttribute(sessionId, value);
        addSession(session);
    }

    public void addSession(Session session) {
        SessionRepository.addSession(session.getId(), session);
    }

    public Session findById(String id) {
        return SessionRepository.findById(id);
    }

}
