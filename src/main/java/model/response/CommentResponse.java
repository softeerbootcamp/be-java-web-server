package model.response;

import java.time.LocalDate;

public class CommentResponse {

    private final String author;
    private final String content;
    private final LocalDate createdDate;

    public CommentResponse(String author, String content, LocalDate createdDate) {
        this.author = author;
        this.content = content;
        this.createdDate = createdDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
