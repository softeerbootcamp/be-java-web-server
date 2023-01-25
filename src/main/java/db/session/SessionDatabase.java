package db.session;

import model.Session;

import java.util.Collection;

public interface SessionDatabase {
    void addSession(Session session);

    Session findSessionById(String sessionId);

    void deleteSession(String sessionId);

    Collection<Session> findAll();
}
