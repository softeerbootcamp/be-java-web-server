package db;

import com.google.common.collect.Maps;
import model.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommentDatabase {

    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    public static void addCookie(HttpSession session) {
        sessions.put(session.getSessionId(), session);
    }

    public static Optional<HttpSession> findSessionById(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public static void deleteCookie(String sessionId){
        sessions.remove(sessionId);
    }

    public static List<HttpSession> findAll() {
        return new ArrayList<>(sessions.values());
    }
}
