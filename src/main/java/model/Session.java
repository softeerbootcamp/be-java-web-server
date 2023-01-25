package model;

import java.time.LocalDateTime;

public class Session {
    public final static long SESSION_EXPIRE_HOUR = 1;

    public final static long SESSION_EXTEND_HOUR = 1;

    private final String sid;

    private final String userId;

    private LocalDateTime expirationTime;

    private Session(String sid, String user, LocalDateTime expirationTime) {
        this.sid = sid;
        this.userId = user;
        this.expirationTime = expirationTime;
    }

    public static Session of(String sid, String userId) {
        return new Session(sid, userId, LocalDateTime.now().plusHours(SESSION_EXPIRE_HOUR));
    }

    public String getSid() {
        return sid;
    }

    public String getUserId() {
        return userId;
    }

    public void extendExpirationTime() {
        expirationTime = LocalDateTime.now().plusHours(SESSION_EXTEND_HOUR);
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
