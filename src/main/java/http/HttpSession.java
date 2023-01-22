package http;

import java.time.LocalDateTime;

public class HttpSession {

    private final String sid;
    private final String userName;
    private LocalDateTime expiredAt;

    private HttpSession(
            String sid,
            String userName,
            LocalDateTime expiredAt
    ) {
        this.sid = sid;
        this.userName = userName;
        this.expiredAt = expiredAt;
    }

    public static HttpSession of(String sid, String userName) {
        return new HttpSession(
                sid,
                userName,
                LocalDateTime.now().plusHours(1)
        );
    }

    public String getSid() {
        return sid;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

}
