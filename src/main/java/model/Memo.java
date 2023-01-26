package model;

import java.time.LocalDateTime;

public class Memo {
    private Long memoId;
    private final String writer;
    private final String content;
    private final LocalDateTime createdAt;

    private Memo(String writer, String content, LocalDateTime createdAt) {
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }

    private Memo(Long memoId, String writer, String content, LocalDateTime createdAt) {
        this.memoId = memoId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Memo of(String writer, String content, LocalDateTime createdAt) {
        return new Memo(writer, content, createdAt);
    }

    public static Memo of(Long memoId, String writer, String content, LocalDateTime createdAt) {
        return new Memo(memoId, writer, content, createdAt);
    }

    public void setMemoId(Long memoId) {
        this.memoId = memoId;
    }

    public Long getMemoId() {
        return memoId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Memo [memoId = " + memoId + ", writer=" + writer + ", content=" + content + ", createdAt=" + createdAt + "]";
    }
}
