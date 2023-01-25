package model;

public class Article {
    private String date;
    private String userId;
    private String body;

    public Article(String date, String userId, String body){
        this.date = date;
        this.userId = userId;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }
}
