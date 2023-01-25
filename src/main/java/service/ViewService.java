package service;

import model.domain.User;
import model.request.Request;
import model.session.Sessions;

public class ViewService {
    public byte[] makeResponseBodyWhenLoginSuccess(byte[] body, Request request) {
        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalIndexHtml = new String(body);
        String resultIndexHtml = originalIndexHtml.replace("로그인", user.getName());
        resultIndexHtml = resultIndexHtml.replace("user/login.html", "user/profile.html");
        return resultIndexHtml.getBytes();
    }
}
