package model;

import java.time.LocalDateTime;

public class Memo {
    private Long memoId;
    private final String writer;
    private final String content;
    private LocalDateTime createdAt;

    public Memo(String writer, String content) {
        this.writer = writer;
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
        return "Memo [memoId = " + memoId + ", writer=" + writer + ", content=" + content + "]";
    }
}
