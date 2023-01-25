package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Memo {

    private final String userName;
    private final String createdAt;
    private final String content;

    public Memo(String userName, String createdAt, String content) {
        this.userName = userName;
        this.createdAt = createdAt;
        this.content = content;
    }

    public static Memo from(String userName, String content) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Memo(
                userName,
                now.format(formatter),
                content
        );
    }

    public static Memo from(String userName, String createdAt, String content) {
        return new Memo(
                userName,
                createdAt,
                content
        );
    }

    public String getUserName() {
        return userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }
}
