package model;

import java.util.UUID;

public class Session {

    public static final String SESSION_ID = "sid";

    private String uuid;

    private final User user;

    public Session( User user) {
        this.uuid = makeSessionId();
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    private String makeSessionId() {
        return UUID.randomUUID().toString();
    }


    public User getUser() {
        return user;
    }
}
