package db;

import Request.HttpRequest;
import Session.Session;
import model.User;
import util.ManageDB;

import java.util.HashMap;
import java.util.Map;

public class SessionDb {
    private static final Map<String, Session> sessionDb = new HashMap<>();

    public static String saveNewSession(HttpRequest httpRequest) {
        User user = ManageDB.findUserById(httpRequest.getParams().get("userId"));
        Session session = new Session(user);
        sessionDb.put(session.getSessionId(), session);
        return session.getSessionId();
    }

    public static Session getSession(String id) {
        return sessionDb.get(id);
    }
}
