package util;

import db.SessionStorage;
import model.User;

import java.util.UUID;

public class Session {
    private UUID sessionId;
    private String userId;

    private int maxInactiveInterval = 5;
    private long creationTime = 0L;
    private long lastAcceptedTime = creationTime;

    private Session(String userId) {
        this.sessionId = UUID.randomUUID();
        this.userId = userId;
    }

    public static Session createSessionWith(User user) {
        Session session = new Session(user.getUserId());
        long timeNow = System.currentTimeMillis();
        session.setCreationTime(timeNow);
        return session;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
        this.lastAcceptedTime = creationTime;
    }
    public long getIdleTime() {
        long timeNow = System.currentTimeMillis();
        long timeIdle = lastAcceptedTime - timeNow;
        return timeIdle;
    }
    public boolean isValid() {
        int timeIdle = (int) (getIdleTime() / 1000L);
        if (timeIdle >= maxInactiveInterval) {
            expire();
            return false;
        }
        return true;
    }
    public void expire() {
        SessionStorage.remove(this);
    }
}
