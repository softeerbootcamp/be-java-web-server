package util;

import model.domain.User;
import model.request.Request;
import model.session.Sessions;

import java.util.Collection;

public class ResponseBodyUtils {
    public static byte[] makeResponseBodyWhenLoginSuccess(byte[] body, Request request) {
        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalIndexHtml = new String(body);
        String resultIndexHtml = originalIndexHtml.replace("로그인", user.getName());
        resultIndexHtml = resultIndexHtml.replace("user/login.html", "user/profile.html");
        return resultIndexHtml.getBytes();
    }

    public static byte[] makeResponseBodyWhenLoginAndProfileHtml(byte[] body, Request request) {
        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalProfileHtml = new String(body);
        String resultProfileHtml = originalProfileHtml.replace("자바지기", user.getName());
        resultProfileHtml = resultProfileHtml.replace("javajigi@slipp.net", user.getEmail());
        return resultProfileHtml.getBytes();
    }

    public static byte[] makeResponseBodyWhenLoginAndUserList(byte[] body, Request request, Collection<User> users) {
        StringBuilder userList = new StringBuilder();
        int row = 0;

        for(User user : users) {
            userList.append("<tr><th scope=\"row\">")
                    .append(++row)
                    .append("</th><td>")
                    .append(user.getUserId()).append("</td><td>")
                    .append(user.getName())
                    .append("</td><td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }

        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalListHtml = new String(body);
        String[] splitListHtml = originalListHtml.split("<tbody>");
        String resultListHtml = splitListHtml[0] + "<tbody>" + userList + splitListHtml[1];
        resultListHtml = resultListHtml.replace("로그인", user.getName());
        resultListHtml = resultListHtml.replace("user/login.html", "user/profile.html");
        return resultListHtml.getBytes();
    }
}
