package model;

import java.time.LocalDateTime;
public class Session {
    public static final int TIMEOUT_MINUTE = 60;
    private final String ssid;
    private LocalDateTime expiredAt;
    private final String userId;

    public Session(String ssid, String userId){
        this.userId = userId;
        this.ssid = ssid;
        expiredAt = LocalDateTime.now().plusMinutes(60);
    }

    public String getUserId(){
        return userId;
    }

    public String getSSID(){
        return ssid;
    }

    public LocalDateTime getExpiredAt(){
        return expiredAt;
    }

    public void refresh(){
        expiredAt = LocalDateTime.now().plusMinutes(TIMEOUT_MINUTE);
    }

    public String toString(){
        return String.format("SID=%s; Path=/; Expires=%s; HttpOnly; Secure", ssid, expiredAt, userId);
    }

    public void expire() {
        expiredAt = LocalDateTime.now();
    }
}
