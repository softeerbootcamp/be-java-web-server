package model;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createdTime;

    private Post(int id, String title, String content, String userId, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdTime = createdTime;
    }

    public static Post of(int id, String title, String content, String userId, LocalDateTime createdTime) {
        return new Post(id, title, content, userId, createdTime);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
