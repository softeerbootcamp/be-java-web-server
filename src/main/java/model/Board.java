package model;

public class Board {
    private final String date;
    private final String author;
    private final String content;

    private Board(String date, String author, String content) {
        this.date = date;
        this.author = author;
        this.content = content;
    }

    public static Board of(String date, String author, String content) {
        return new Board(date, author, content);
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

    @Override
    public String toString() {
        return "Board [date=" + date + ", author=" + author + ", content=" + content + "]";
    }

}
