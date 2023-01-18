package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final String sessionId;
    private final Map<String, String> attributes = new HashMap<>();

    private Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public static Session of(String sessionId, String userId) {
        Session session = new Session(sessionId);
        session.setAttribute(SessionConst.USER_ID, userId);

        return session;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }
}
