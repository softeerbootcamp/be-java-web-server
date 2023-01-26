package db;

import com.google.common.collect.Maps;
import http.common.Session;
import model.Post;
import model.User;

import java.util.*;

public class Database {
    private static Map<String, Session> sessions = Maps.newHashMap();

    public static void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Session getSession(String sid) {
        return sessions.get(sid);
    }
}
