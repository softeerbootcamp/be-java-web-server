package model;

import java.util.List;

public class Post {
    private Long postId;
    private String author;
    private String title;
    private String contents;

    public Post (String initAuthor, String initTitle, String initContents){
        author = initAuthor;
        title = initTitle;
        contents = initContents;
    }

    public Post(List<String> row){
        postId = Long.valueOf(row.get(0));
        author = row.get(1);
        title = row.get(2);
        contents = row.get(3);
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


    @Override
    public String toString(){
        return postId + " " + author + " " + title + " " + contents;
    }
}
