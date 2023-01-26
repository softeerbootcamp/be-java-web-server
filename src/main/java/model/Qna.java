package model;

import Request.HttpRequest;

import java.time.LocalDateTime;
import java.util.Date;

public class Qna {
    private int id;
    private String name;
    private String subject;
    private String content;
    private String date;

    public Qna(int id, String name, String subject, String content, String date) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }
    public static Qna createQna(HttpRequest httpRequest) {
        String name = httpRequest.getParams().get("writer");
        String subject = httpRequest.getParams().get("title");
        String content = httpRequest.getParams().get("contents");
        String date = LocalDateTime.now().toString();
        return new Qna(-1, name, subject, content, date);
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Qna{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
