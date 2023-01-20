package db;

import utils.SessionIdGenerator;

import java.util.Map;

public class UserIdSession {
    private static Map<String, String> sessions;

    public static String getUserId(String sessionId)
    {
        return sessions.get(sessionId);
    }

    public static String addUserId(String userId)
    {
        String randSessionId = SessionIdGenerator.generateSessionId();
        sessions.put(randSessionId, userId);
        return randSessionId;
    }
}
