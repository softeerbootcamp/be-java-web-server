package model;

import java.time.LocalDateTime;

public class UserSession {
    private final String userId;
    private final String name;
    private final LocalDateTime createdTime;
    private LocalDateTime expiredTime;

    public UserSession(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.createdTime = LocalDateTime.now();
        this.expiredTime = LocalDateTime.now().plusMinutes(30L);
    }

    public void expire() {
        this.expiredTime = LocalDateTime.now().minusDays(1L);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }
}
