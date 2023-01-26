package model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HttpSession {

    private String sessionId;

    private String userId;
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime expireDate;
    private LocalDateTime lastAccessDate;
    private String path;
    private int maxAge;
    private static final int COOKIE_DURATION_DAY = 7;

    public HttpSession(String sessionId, String userId, String username, LocalDateTime now) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
        this.path = "/";
        this.createDate = now;
        updateCookieTimeInfo(now);
    }

    public boolean isValid(){
        return maxAge > 0;
    }

    public void invalidateSession(){
        maxAge = 0;
    }

    public void updateCookieTimeInfo(LocalDateTime now){
        this.lastAccessDate = now;
        this.expireDate = now.plusDays(COOKIE_DURATION_DAY);  //extend the date of expiration
        this.maxAge = COOKIE_DURATION_DAY * 24 * 60 * 60;
    }

    @Override
    public String toString(){
        return "sid = " + sessionId
                + "; Path = " + path
                + "; Max-age = " + maxAge;
    }

}
