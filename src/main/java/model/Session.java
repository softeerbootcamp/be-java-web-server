package model;

import java.time.LocalDateTime;

public class Session {
    public final static long SESSION_EXPIRE_HOUR = 1;

    public final static long SESSION_EXTEND_HOUR = 1;

    private final String sid;

    private final User user;

    private LocalDateTime expirationTime;

    private Session(String sid, User user, LocalDateTime expirationTime) {
        this.sid = sid;
        this.user = user;
        this.expirationTime = expirationTime;
    }

    public static Session of(String sid, User user) {
        return new Session(sid, user, LocalDateTime.now().plusHours(SESSION_EXPIRE_HOUR));
    }

    public String getSid() {
        return sid;
    }

    public User getUser() {
        return user;
    }

    public void extendExpirationTime() {
        expirationTime = LocalDateTime.now().plusHours(SESSION_EXTEND_HOUR);
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
