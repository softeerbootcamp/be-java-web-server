package repository;

import model.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionRepo {
    private static final Map<String, Session> sessionMap;

    static{
        sessionMap = new HashMap<>();
    }

    public static Session createSession(String userId) {
        String SSID = UUID.randomUUID().toString();
        Session sess = new Session(SSID, userId);
        sessionMap.put(SSID, sess);
        return sess;
    }

    public static Session findBySSID(String ssid){
        if(sessionMap.containsKey(ssid))
            return sessionMap.get(ssid);
        return null;
    }

    public static boolean isExpired(String ssid){
        Session sess = findBySSID(ssid);
        if(sess != null)
            return sess.getExpiredAt().isAfter(LocalDateTime.now());
        return false;
    }

    public static void deleteSession(String ssid){
        Session session = findBySSID(ssid);
        if(session == null)
            return;
        session.expire();
    }
}
