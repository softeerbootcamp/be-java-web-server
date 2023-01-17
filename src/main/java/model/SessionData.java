package model;

public class SessionData {
    private final long timeStamp;
    private String userId;
    private String name;
    private String email;


    private SessionData(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.timeStamp = System.currentTimeMillis();
    }

    public static SessionData from(User user) {
        return new SessionData(user.getUserId(), user.getName(), user.getEmail());
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
