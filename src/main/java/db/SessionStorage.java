package db;

import model.User;
import util.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {
    private static Map<UUID, String> sessions = new HashMap<>();

    public static void addSession(Session session) {
        sessions.put(session.getSid(), session.getId());
    }
}
