package service.session;

import db.session.SessionDatabase;
import exception.NotLogInException;
import model.Session;
import model.User;

import java.util.Collection;
import java.util.UUID;

public class SessionServiceImpl implements SessionService {
    private static final long SESSION_LIFE_TIME_MS = 300000L;
    private static SessionService sessionService;
    private final SessionDatabase sessionDatabase;


    private SessionServiceImpl(SessionDatabase sessionDatabase) {
        this.sessionDatabase = sessionDatabase;
    }

    public static SessionService getInstance(SessionDatabase sessionDatabase) {
        if (sessionService == null) sessionService = new SessionServiceImpl(sessionDatabase);
        return sessionService;
    }

    @Override
    public String createSession(User data) {
        String sessionID = UUID.randomUUID().toString();
        sessionDatabase.addSession(Session.create(sessionID, data.getUserId()));
        return sessionID;
    }

    @Override
    public Session getSession(String sessionId) {
        return sessionDatabase.findSessionById(sessionId);
    }

    @Override
    public void removeSession(String sessionId) {
        sessionDatabase.deleteSession(sessionId);
    }

    @Override
    public void validateSession(String sessionId) throws NotLogInException {
        if (sessionId == null || getSession(sessionId) == null)
            throw new NotLogInException();
    }

    @Override
    public void checkExpiredSessions() {
        long currentTime = System.currentTimeMillis();
        Collection<Session> sessions = sessionDatabase.findAll();
        sessions.stream().filter(session -> currentTime - session.getTimeStamp() > SESSION_LIFE_TIME_MS)
                .forEach(session -> removeSession(session.getSessionId()));
    }
}
