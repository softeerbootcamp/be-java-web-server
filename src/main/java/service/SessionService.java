package service;

import db.SessionRepository;
import model.Session;

import java.util.UUID;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session makeSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, userId);
        sessionRepository.addSession(sessionId, session);

        return session;
    }

    public void validateHasSession(String id) {
        Session session = sessionRepository.findById(id);
        if (session == null)
            throw new NullPointerException("Session not found");
    }

    public String getUserId(String sessionId) {
        return sessionRepository
                .findById(sessionId)
                .getUserId();
    }

}
