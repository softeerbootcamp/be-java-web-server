package http.common;

import model.User;

public class Session {

    public static final String SESSION_FIELD_NAME = "sid";
    private String id;
    private User user;

    public Session(User user) {
        this.user = user;
        this.id = generateId(user.getUserId());
    }

    private String generateId(String userId) {
        return userId + userId;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    // todo: expiration date 추가해서 유효성 검사
    public boolean isValid() {
        return true;
    }
}
