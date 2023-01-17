package model;

import java.time.LocalDateTime;

public class HttpCookie {

    private String sessionId;
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime expireDate;
    private LocalDateTime lastAccessDate;
    private String path;
    private int maxAge;
    private static final int COOKIE_DURATION_DAY = 7;

    public HttpCookie(String sessionId, String username, LocalDateTime now) {
        this.sessionId = sessionId;
        this.username = username;
        this.path = "/";
        this.createDate = now;
        updateCookieTimeInfo(now);
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isValid(LocalDateTime accessTime){
        return this.expireDate.isBefore(accessTime);
    }

    public void invalidateCookie(){
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
