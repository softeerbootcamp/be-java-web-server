package service;

import db.SessionRepository;
import exception.NonLogInException;
import model.Session;

import java.util.UUID;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session makeSession(String userId, String userName) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, userId, userName);
        sessionRepository.addSession(sessionId, session);

        return session;
    }

    public void validateHasSession(String id) {
        Session session = sessionRepository.findById(id);
        if (session == null)
            throw new NonLogInException("Session not found");
    }

    public String getUserId(String sessionId) {
        return sessionRepository
                .findById(sessionId)
                .getUserId();
    }

    public String getUserName(String sessionId) {
        return sessionRepository
                .findById(sessionId)
                .getUserName();
    }

}
