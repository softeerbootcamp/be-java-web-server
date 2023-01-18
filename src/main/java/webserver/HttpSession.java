package webserver;

import model.User;

import java.util.Map;

public class HttpSession { // 각 사용자마다 해당되는 고유한 세션이 있음. 이 세션 안에 사용자의 정보가 담기는 거임

    private final String sessionId;
    private final User userInfo;
    private boolean valid;

    public HttpSession(String sessionId, User userInfo) {
        this.sessionId = sessionId;
        this.userInfo = userInfo;
        this.valid = true;
    }

    public String getId() {
        return sessionId;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public boolean isValid() {
        return valid;
    }

    public void inValidate() {
        this.valid = false;
    }
}
