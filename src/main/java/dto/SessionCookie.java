package dto;

import java.util.Map;

public class SessionCookie extends Session {
    private Map<String, String> cookieOptions;

    public SessionCookie(String userId, String username, Map<String, String> options) {
        super(userId, username);
        this.cookieOptions = options;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s=%s;", "sid", super.sessionId));

        cookieOptions.forEach((k, v) -> sb.append(String.format(" %s=%s;", k, v)));

        return sb.toString();
    }
}
