package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Post {
    private Long postId;
    private final String author;
    private final String title;
    private final String contents;
    private LocalDateTime createdAt;

    public Post (String initAuthor, String initTitle, String initContents){
        author = initAuthor;
        title = initTitle;
        contents = initContents;
    }

    public Post(Map<String, String> row) {
        postId = Long.valueOf(row.get("id"));
        author = row.get("author");
        title = row.get("title");
        contents = row.get("contents");
        createdAt = LocalDateTime.parse(row.get("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public void setPostId(Long postId){
        this.postId = postId;
    }
    public String getAuthor(){
        return author;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContents() {
        return contents;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    @Override
    public String toString() {
        return postId + " " + author + " " + title + " " + contents;
    }
}
