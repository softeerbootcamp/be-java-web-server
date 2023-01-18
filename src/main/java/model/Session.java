package model;

import java.util.UUID;

public class Session {

    public static final String SESSION_ID = "sid";

    private String uuid;


    public Session(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public static String makeSessionId() {
        return UUID.randomUUID().toString();
    }

}
