package model.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final String sessionId;
    private final Map<String, Object> sessionData;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.sessionData = new HashMap<>();
    }

    public void setSessionData(String name, Object value) {
        sessionData.put(name, value);
    }
}
