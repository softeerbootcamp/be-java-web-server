package model;

import java.time.LocalDateTime;

public class Memo {
    private int memoId;
    private String content;
    private String author;
    private String dateTime;

    private Memo(int memoId, String content, String author, String dateTime) {
        this.memoId = memoId;
        this.content = content;
        this.author = author;
        this.dateTime = dateTime;
    }

    public Memo create(int memoId, String content, String author) {
        return new Memo(memoId, content, author, LocalDateTime.now().toString());
    }

    public Memo createWithDT(int memoId, String content, String author, String dateTime) {
        return new Memo(memoId, content, author, dateTime);
    }

    public int getMemoId() {
        return memoId;
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
