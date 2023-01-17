package repository;

import model.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionRepo {
    private static final Map<String, Session> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    public static Session createSession(String userId) {
        String SSID = UUID.randomUUID().toString();
        Session sess = new Session(SSID, userId);
        sessionMap.put(SSID, sess);
        return sess;
    }

    public static Optional<Session> findBySSID(String ssid) {
        return Optional.ofNullable(sessionMap.get(ssid));
    }

    public static void deleteSession(String ssid) {
        Optional<Session> optionalSession = findBySSID(ssid);
        optionalSession.ifPresent(session -> {
            session.expire();
            sessionMap.remove(ssid);
        });
    }
}
