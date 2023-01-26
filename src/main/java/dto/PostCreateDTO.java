package dto;

import model.User;

import java.time.LocalDateTime;
import java.util.Map;

public class PostCreateDTO {
    public static final String TITLE = "title";
    public static final String CONTENT = "contents";

    private User writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    private PostCreateDTO(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }

    public static PostCreateDTO of(User user, Map<String, String> parameters) {
        return new PostCreateDTO(user, parameters.get(TITLE), parameters.get(CONTENT));
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean isEmpty() {
        return title.isEmpty() || content.isEmpty();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}

