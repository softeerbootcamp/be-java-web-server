package service;

import db.SessionRepository;
import model.Session;

import java.util.UUID;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String makeSessionId() {
        return UUID.randomUUID().toString();
    }

    public void makeSession(String sessionId, String value) {
        Session session = new Session(sessionId);
        session.setAttribute(sessionId, value);
        sessionRepository.addSession(session.getId(), session);
    }

    public void validateHasSession(String id) {
        Session session = sessionRepository.findById(id);
        if (session == null)
            throw new NullPointerException("Session not found");
    }

}
