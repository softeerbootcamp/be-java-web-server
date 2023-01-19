package model;

public class Session {
    private final long timeStamp;
    private final String userId;
    private final String name;
    private final String email;


    private Session(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.timeStamp = System.currentTimeMillis();
    }

    public static Session from(User user) {
        return new Session(user.getUserId(), user.getName(), user.getEmail());
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
