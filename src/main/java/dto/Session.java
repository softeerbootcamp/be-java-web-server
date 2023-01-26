package dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    protected String sessionId;
    protected final LocalDateTime createDate;
    protected String userId;
    protected String userName;

    protected Session(String userId, String userName) {
        this.sessionId = String.valueOf(UUID.randomUUID());
        this.userId = userId;
        this.userName = userName;
        this.createDate = LocalDateTime.now();
    }
    public Session(String sessionId, String userId, String userName, LocalDateTime createDate) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
        this.createDate = createDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getUserName() {
        return userName;
    }
}
