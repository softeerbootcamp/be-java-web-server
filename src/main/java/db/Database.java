package db;

import com.google.common.collect.Maps;
import http.common.Session;
import model.User;
import http.common.Session;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();
    private static Map<String, Session> sessions = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static void deleteUser(String Userid) {
        users.remove(Userid);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void addSession(Session session) {
        sessions.put(session.getId(), session);
    }
    public static Session getSession(String sid) {
        return sessions.get(sid);
    }
}
