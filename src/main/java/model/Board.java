package model;

public class Board {
    private String writer;
    private String contents;

    public Board(String writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
