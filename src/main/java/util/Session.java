package util;

import model.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Session {
    private static final int leftLimit = 48;
    private static final int rightLimit = 122;
    private static final int sessionIdLength = 10;
    public static Map<String, User> loginSession = new HashMap<>();
    public static String newLoginSession(User user){
        String sessionId = new Random().ints(leftLimit,rightLimit+1)
                .filter(i->(i<=57 || i>=65) && (i<=90 || i>=97))
                .limit(sessionIdLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        loginSession.put(sessionId, user);
        return sessionId;
    }

    public static Optional<User> findUserBySessionId(String sessionId){
        return Optional.of(loginSession.get(sessionId));
    }
}
