package db;

import com.google.common.collect.Maps;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class SessionStorage {
    private static final Logger logger = LoggerFactory.getLogger(SessionStorage.class);

    private static Map<String, User> sessionUsers = Maps.newHashMap();

    public static void addSession(String sid, User user) {
        sessionUsers.put(sid, user);
    }

    public static Optional<User> findBySessionId(String sid) {
        return Optional.ofNullable(sessionUsers.get(sid));
    }

    public static boolean isValidate(String sid) {
        return sessionUsers.containsKey(sid);
    }

    public static void cleanAll() {
        sessionUsers.clear();
    }
}
