package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Post {
    private Long postId;
    private final String author;
    private final String title;
    private final String contents;
    private LocalDateTime created_at;

    public Post (String initAuthor, String initTitle, String initContents){
        author = initAuthor;
        title = initTitle;
        contents = initContents;
    }

    public Post(List<String> row) {
        postId = Long.valueOf(row.get(0));
        author = row.get(1);
        title = row.get(2);
        contents = row.get(3);

        created_at = LocalDateTime.parse(row.get(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
        return created_at.toString();
    }

    @Override
    public String toString() {
        return postId + " " + author + " " + title + " " + contents;
    }
}
