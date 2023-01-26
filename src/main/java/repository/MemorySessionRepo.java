package repository;

import model.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemorySessionRepo implements SessionRepo {

    private final Map<String, Session> sessionMap;
    public MemorySessionRepo() {
        this.sessionMap = new HashMap<>();
    }

    public void addSession(Session session) {
        sessionMap.put(session.getSSID(), session);
    }

    public Optional<Session> findBySSID(String ssid) {
        return Optional.ofNullable(sessionMap.get(ssid));
    }

    public void deleteBySSID(String ssid) {
        Optional<Session> optionalSession = findBySSID(ssid);
        optionalSession.ifPresent(session -> {
            session.expire();
            sessionMap.remove(ssid);
        });
    }
}
