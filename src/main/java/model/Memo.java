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

    public void setMemoId(Long memoId) {
        this.memoId = memoId;
    }

    public Long getMemoId() {
        return memoId;
    }

    @Override
    public String toString() {
        return "Memo [memoId = " + memoId + ", writer=" + userId + ", content=" + content + "]";
    }
}
