package dao;

import java.util.Date;

public class PostDAO {
    private Long id;
    private Long uid;
    private String title;
    private String content;
    private Date createdDate;

    private PostDAO(Long id, Long uid, String title, String content, Date createdDate) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static PostDAO of(Long postId, Long userId, String title, String content, Date createdDate) {
        return new PostDAO(postId, userId, title, content, createdDate);
    }

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
