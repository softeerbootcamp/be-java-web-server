package model;

import java.time.LocalDateTime;

public class UserSession {
    private final String userId;
    private final String name;
    private final LocalDateTime createdDate;
    private LocalDateTime expiredDate;

    public UserSession(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.createdDate = LocalDateTime.now();
        this.expiredDate = LocalDateTime.now().plusMinutes(30L);
    }

    public void expire() {
        this.expiredDate = LocalDateTime.now().minusDays(1L);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }
}
