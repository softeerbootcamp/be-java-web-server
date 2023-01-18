package webserver;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HttpSessionManager {

    private static final Map<String, HttpSession> sessions = new HashMap<>(); //해당 맵에서의 키는 각 value인 HttpSession의 키와 동일함

    public static HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);
        return session;
    }

    private static void removeSession(HttpSession session) {
        if (session != null) {
            sessions.remove(session.getId());
        }
    }

    public static HttpSession createSession(User user) {
        return new HttpSession(UUID.randomUUID().toString(), user);
    }

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }
}
