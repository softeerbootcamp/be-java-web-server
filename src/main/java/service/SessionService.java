package service;

import db.SessionDatabase;
import model.Session;

import java.util.Optional;

public class SessionService {
    private static final SessionService instance;

    private final SessionDatabase sessionDatabase;

    static {
        instance = new SessionService();
    }

    private SessionService() {
        sessionDatabase = SessionDatabase.getInstance();
    }

    public static SessionService getInstance() {
        return instance;
    }

    public void addSession(Session session) {
        sessionDatabase.add(session);
    }

    public Optional<Session> findSession(String sid) {
        return sessionDatabase.findById(sid);
    }

    public void removeSession(String sid) {
        sessionDatabase.deleteSession(sid);
    }
}
