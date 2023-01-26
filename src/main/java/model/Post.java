package model;

import dto.PostCreateDTO;

import java.time.LocalDateTime;

public class Post {

    private Long id;
    private User user;
    private String title;
    private String content;

    private LocalDateTime createdDate;

    public Post(User user, PostCreateDTO postCreateDTO) {
        this.user = user;
        this.title = postCreateDTO.getTitle();
        this.content = postCreateDTO.getContent();
        this.createdDate = LocalDateTime.now();
    }

    public static Post of(User user, PostCreateDTO postCreateDTO) {
        return new Post(user, postCreateDTO);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        return createdDate.toString();
    }
}
