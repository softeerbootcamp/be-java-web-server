package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements Comparable<Post> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String title;
    private String content;
    private String writer;
    private String dateTime;


    private Post(String title, String content, String writer, String dateTime) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.dateTime = dateTime;
    }

    public static Post create(String title, String content, String writer) {
        return new Post(title, content, writer, LocalDateTime.now().format(formatter));
    }

    public static Post createWithDT(String title, String content, String writer, String dateTime) {
        return new Post(title, content, writer, dateTime);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public int compareTo(Post o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
