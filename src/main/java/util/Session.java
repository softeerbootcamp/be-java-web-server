package util;

import db.SessionStorage;
import model.User;

import java.util.UUID;

public class Session {
    private UUID sid;
    private String id;

    private int maxInactiveInterval = 5;
    private long creationTime = 0L;
    private long lastAcceptedTime = creationTime;

    private Session(String id) {
        this.sid = UUID.randomUUID();
        this.id = id;
    }
    public static Session createSessionWith(User user) {
        return new Session(user.getUserId());
    }

    public UUID getSid() {
        return sid;
    }

    public String getId() {
        return id;
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
    public void expire() {
        SessionStorage.remove(this);
    }
}
