package model;

public class Session {
    private final long timeStamp;
    private final String sessionId;
    private final String userId;


    private Session(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.timeStamp = System.currentTimeMillis();
    }

    public static Session of(String sessionId, String userId) {
        return new Session(sessionId, userId);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
