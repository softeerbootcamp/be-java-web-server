package db;

import request.HttpRequest;
import model.Session;
import model.User;
import util.UserDbUtil;

import java.util.HashMap;
import java.util.Map;

public class SessionDb {
    private static final Map<String, Session> sessionDb = new HashMap<>();

    public static String saveNewSession(HttpRequest httpRequest) {
        User user = UserDbUtil.findUserById(httpRequest.getParams().get("userId"));
        Session session = new Session(user);
        sessionDb.put(session.getSessionId(), session);
        return session.getSessionId();
    }

    public static void putSession(Session s) {
        sessionDb.put(s.getSessionId(), s);
    }

    public static Session getSession(String id) {
        return sessionDb.get(id);
    }
}
