package model;

import java.util.UUID;

public class Session {

    private String uuid;


    public Session(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
