package model;

import model.User;

import java.util.UUID;

public class Session {
    private final User user;
    private final String sessionId;

    public Session(User user) {
        this.user = user;
        this.sessionId = generateSessionId();
    }

    public String getUserId() {
        return this.user.getUserId();
    }

    public String getSessionId() {
        return sessionId;
    }

    private String generateSessionId(){
        return UUID.randomUUID().toString();
    }

}
