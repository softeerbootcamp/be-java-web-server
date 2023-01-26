package model;

import java.time.LocalDateTime;

public class Memo {
    private Long memoId;
    private final String userId;
    private final String content;
    private LocalDateTime createdAt;

    public Memo(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Memo [memoId = " + memoId + ", writer=" + userId + ", content=" + content + "]";
    }
}
