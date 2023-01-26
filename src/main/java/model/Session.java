package model;

public class Session {

    private final String sessionId;
    private final String userId;
    private final String userName;

    private Session(String sessionId, String userId, String userName) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
    }

    public static Session of(String sessionId, String userId, String userName) {
        return new Session(sessionId, userId, userName);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Session [sessionId=" + sessionId + ", userId=" + userId + ", userName=" + userName + "]";
    }

}
