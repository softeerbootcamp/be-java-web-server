package model;

public class SessionData {
    private String userId;
    private String name;
    private String email;

    private SessionData(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static SessionData from(User user) {
        return new SessionData(user.getUserId(), user.getName(), user.getEmail());
    }
}
