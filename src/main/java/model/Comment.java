package model;

public class Comment {

    private final String userId;
    private final String author;
    private final String content;

    public Comment(String userId, String author, String content) {
        this.userId = userId;
        this.author = author;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
