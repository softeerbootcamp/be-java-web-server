package service;

import db.SessionRepository;
import model.Session;

public class SessionService {

    private SessionService() {
    }

    private static class SessionServiceHolder {
        private static final SessionService SESSION_SERVICE = new SessionService();
    }

    public static SessionService getInstance() {
        return SessionServiceHolder.SESSION_SERVICE;
    }

    public void addSession(Session session) {
        SessionRepository.addSession(session.getId(), session);
    }

    public Session findById(String id) {
        return SessionRepository.findById(id);
    }
}
