package dto;

import java.time.LocalDateTime;

public class Comment {
    private int commentId;
    private String body;
    private String userId;
    private String userName;
    private final LocalDateTime createDate;

    public Comment(String body, String userId, String userName) {
        this(0, body, userId, userName, LocalDateTime.now());
    }

    public Comment(int commentId, String body, String userId, String userName, LocalDateTime createDate) {
        this.commentId = commentId;
        this.body = body;
        this.userId = userId;
        this.userName = userName;
        this.createDate = createDate;
    }

    public int getCommentId() {
        return commentId;
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

    public String getBody() {
        return body;
    }
}
