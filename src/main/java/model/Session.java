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

    private Session(String sessionId, String userId, long timeStamp) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }


    public static Session create(String sessionId, String userId) {
        return new Session(sessionId, userId);
    }

    public static Session createWithTimeStamp(String sessionId, String userId, long timeStamp) {
        return new Session(sessionId, userId, timeStamp);
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
