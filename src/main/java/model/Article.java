package model;

public class Article {
    private String writerId;
    private String content;
    private String date;

    public Article(String writerId, String content, String date)
    {
        this.writerId = writerId;
        this.content = content;
        this.date = date;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
