package db;

import com.google.common.collect.Maps;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class Session {
    private static final Logger logger = LoggerFactory.getLogger(Session.class);
    private static Map<String, String> sessions = Maps.newHashMap();

    public static void addSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        logger.debug("Session User Input : userId = {}", userId + " sessionId = " + sessionId);
        sessions.put(userId, sessionId);
    }

    public static String findSessionIdByUserId(String userId) {
        return sessions.get(userId);
    }

    public static Collection<String> findAll() {
        return sessions.values();
    }
}
