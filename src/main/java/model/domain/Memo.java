package model.domain;

public class Memo {
    private int memoId;
    private String content;
    private String userId;

    public Memo(int memoId, String content, String userId){
        this.memoId = memoId;
        this.content = content;
        this.userId = userId;
    }

    public Memo(String content, String userId){
        this.content = content;
        this.userId = userId;
    }

    public int getMemoId() {
        return memoId;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Memo [memoId=" + memoId + ", content=" + content + ", userId=" + userId + "]";
    }
}
