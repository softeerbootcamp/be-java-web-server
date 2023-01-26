package model;

import dao.PostDAO;
import dto.PostCreateDTO;

import java.time.LocalDateTime;

public class Post {

    private Long id;
    private User user;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    private Post(Long id, User user, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static Post of(User user, PostCreateDTO postCreateDTO) {
        return new Post(null, user, postCreateDTO.getTitle(), postCreateDTO.getContent(), postCreateDTO.getCreatedDate());
    }

    public static Post of(User user, PostDAO postDAO) {
        return new Post(postDAO.getId(), user, postDAO.getTitle(), postDAO.getContent(), postDAO.getCreatedDate());
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
