package model;

public class Board {
    private String writer;
    private String content;

    public Board(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }
}
