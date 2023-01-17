package util;

import model.User;

import java.util.UUID;

public class Session {
    private UUID sid;
    private String id;

    private Session(String id) {
        this.sid = UUID.randomUUID();
        this.id = id;
    }
    public static Session createSessionWith(User user) {
        return new Session(user.getUserId());
    }

    public UUID getSid() {
        return sid;
    }

    public String getId() {
        return id;
    }
}
