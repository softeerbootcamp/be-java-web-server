package db;

import utils.SessionIdGenerator;

import java.util.Map;

public class UserIdSession {
    private Map<String, String> sessions;

    public String getUserId(String sessionId)
    {
        return sessions.get(sessionId);
    }

    public String addUserId(String userId)
    {
        String randSessionId = SessionIdGenerator.generateSessionId();
        sessions.put(randSessionId, userId);
        return randSessionId;
    }
}
