package model;

public class Board {
    private final String date;
    private final String author;
    private final String content;

    public Board(String date, String author, String content) {
        this.date = date;
        this.author = author;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
