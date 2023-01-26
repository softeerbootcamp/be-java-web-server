package http.session;

import model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SessionHandler {
    private static final Map<String, HttpSession> httpSessionMap = new HashMap<>();

    public static HttpSession createSession(User user) {
        String sid = UUID.randomUUID().toString();
        HttpSession httpSession = HttpSession.of(sid, user.getName());
        httpSessionMap.put(sid, httpSession);
        return httpSession;
    }

    public static boolean validateSession(String sid) {
        HttpSession session = httpSessionMap.get(sid);
        return Objects.nonNull(session) && session.getExpiredAt().isAfter(LocalDateTime.now());
    }

    public static HttpSession getSession(String sid) {
        return httpSessionMap.get(sid);
    }

    public static void expireSession(HttpSession httpSession) {
        httpSessionMap.remove(httpSession.getSid());
    }
}
