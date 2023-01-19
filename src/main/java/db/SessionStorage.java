package db;

import com.google.common.collect.Maps;
import exception.SessionExpiredException;
import exception.SessionNotFoundException;
import model.User;
import model.UserSession;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class SessionStorage {

    private static final Map<String, UserSession> sessionUsers = Maps.newHashMap();

    public static void addSession(String sid, User user) {
        sessionUsers.put(sid, new UserSession(user));
    }

    public static UserSession findBySessionId(String sid) {
        UserSession userSession = Optional.ofNullable(sessionUsers.get(sid)).orElseThrow(SessionNotFoundException::new);
        if (userSession.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new SessionExpiredException();
        }
        return userSession;
    }

    public static boolean isValidate(String sid) {
        try {
            findBySessionId(sid);
            return true;
        } catch (SessionExpiredException | SessionNotFoundException e) {
            return false;
        }
    }

    public static void cleanAll() {
        sessionUsers.clear();
    }
}
