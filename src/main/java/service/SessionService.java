package service;

import db.SessionRepository;
import model.Session;

import java.util.UUID;

public class SessionService {

    public static final String LOGIN = "login";

    public String makeSessionId() {
        return UUID.randomUUID().toString();
    }

    public void makeSession(String sessionId, String value) {
        Session session = new Session(sessionId);
        session.setAttribute(sessionId, value);
        SessionRepository.addSession(session.getId(), session);
    }

    public void validateLogin(String id) {
        Session session = SessionRepository.findById(id);
        if (session == null || session.getAttribute(id) != LOGIN)
            throw new NullPointerException("Session not found");
    }

}
