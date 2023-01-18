package model.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final String sessionId;
    private final Map<String, String> sessionData;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.sessionData = new HashMap<>();
    }

    public void setSessionData(String name, String value) {
        sessionData.put(name, value);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, String> getSessionData() {
        return sessionData;
    }
}
