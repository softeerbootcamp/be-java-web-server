package model;

public class Memo {
    private final String userId;
    private final String content;
    private final String date;

    public Memo(String userId, String content, String date) {
        this.userId = userId;
        this.content = content;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
