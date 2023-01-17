package service;

import model.User;

public class Session {

    private String id;
    private User user;

    public Session(User user) {
        this.user = user;
        this.id = generateId(user.getUserId());
    }

    private String generateId(String userId) {
        return userId + userId;
    }
}
