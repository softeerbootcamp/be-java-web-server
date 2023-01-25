package model;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Board {

    private Long id;

    private String writer;
    private String title;
    private String content;

    private Timestamp createTime;

    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createTime =Timestamp.valueOf(LocalDateTime.now());
    }

    public Board(Long id, String writer, String title, String content, Timestamp createTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }


    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
