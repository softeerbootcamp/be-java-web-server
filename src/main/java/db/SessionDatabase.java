package db;

import model.Session;

import java.util.*;

public class SessionDatabase implements Database<Session> {

    private static Map<String, Session> sessionDb = new HashMap<String, Session>();

    @Override
    public Optional<Session> findObjectById(String sessionUUID) {
        return Optional.ofNullable(sessionDb.get(sessionUUID));
    }

    @Override
    public void addData(Session session) {
        sessionDb.put(session.getUuid(), session);
    }

    @Override
    public Collection<Session> findAll() {
        return sessionDb.values();
    }
}
