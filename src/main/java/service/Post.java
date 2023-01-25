package service;

public class Post {
    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final String date;

    public Post(Long id, String writer, String title, String content, String date) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
