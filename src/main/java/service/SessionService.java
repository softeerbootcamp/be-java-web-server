package service;

import db.SessionRepository;
import exception.SessionNotFoundException;
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
        sessionRepository.addSession(sessionId, userId, userName);

        return session;
    }

    public void validateHasSession(String id) throws SessionNotFoundException {
        sessionRepository.findById(id);
    }

    public String getUserId(String sessionId) throws SessionNotFoundException {
        return sessionRepository
                .findById(sessionId)
                .getUserId();
    }

    public String getUserName(String sessionId) throws SessionNotFoundException {
        return sessionRepository
                .findById(sessionId)
                .getUserName();
    }

}
