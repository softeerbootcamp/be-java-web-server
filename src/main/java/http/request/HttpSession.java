package http.request;

import model.User;

import java.util.UUID;

public class HttpSession {
    private String sessionId;
    private final User user;

    public HttpSession(User user) {
        this.user = user;
    }

    // TODO: 원본이 아닌 새로운 User 만들어서 응답 생각해보기
    public User user() {
        return user;
    }

    public String sessionId() {
        if (sessionId == null) {
            this.sessionId = UUID.randomUUID().toString();
        }

        return sessionId;
    }

    public String reissueSID() {
        sessionId = UUID.randomUUID().toString();
        return sessionId;
    }
}
