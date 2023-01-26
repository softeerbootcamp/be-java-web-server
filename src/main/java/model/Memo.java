package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Memo {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String content;
    private String author;
    private String dateTime;


    private Memo(String content, String author, String dateTime) {
        this.content = content;
        this.author = author;
        this.dateTime = dateTime;
    }

    public static Memo create(String content, String author) {
        return new Memo(content, author, LocalDateTime.now().format(formatter));
    }

    public static Memo createWithDT(String content, String author, String dateTime) {
        return new Memo(content, author, dateTime);
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateTime() {
        return dateTime;
    }
}
