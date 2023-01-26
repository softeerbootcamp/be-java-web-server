package util;

import model.domain.User;
import model.request.Request;
import model.session.Sessions;

import java.util.Collection;
import java.util.List;

public class ResponseHtmlUtils {
    public static byte[] makeResponseHtmlWhenLoginSuccess(byte[] body, Request request) {
        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalIndexHtml = new String(body);
        String resultIndexHtml = originalIndexHtml.replace("로그인", user.getName());
        resultIndexHtml = resultIndexHtml.replace("user/login.html", "user/profile.html");
        return resultIndexHtml.getBytes();
    }

    public static byte[] makeResponseHtmlWhenLoginAndProfileHtml(byte[] body, Request request) {
        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalProfileHtml = new String(body);
        String resultProfileHtml = originalProfileHtml.replace("자바지기", user.getName());
        resultProfileHtml = resultProfileHtml.replace("javajigi@slipp.net", user.getEmail());
        return resultProfileHtml.getBytes();
    }

    public static byte[] makeResponseHtmlWhenLoginAndUserList(byte[] body, Request request, Collection<User> users) {
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

    public static byte[] makeResponseHtmlMemoList(byte[] body, List<List<String>> memos) {
        StringBuilder memoList = new StringBuilder();

        for(List<String> memo : memos) {
            memoList.append("<li>")
                    .append("<div class=\"wrap\">")
                    .append("<div class=\"main\">")
                    .append("<strong class=\"subject\"><a>")
                    .append(memo.get(1))
                    .append("</a></strong>")
                    .append("<div class=\"auth-info\">")
                    .append("<i class=\"icon-add-comment\"></i>")
                    .append("<span class=\"time\">")
                    .append(memo.get(2))
                    .append(" </span>")
                    .append("<a class=\"author\">")
                    .append(memo.get(0))
                    .append("</a></div></div></div></li>");
        }

        String originalHtml = new String(body);
        String[] splitHtml = originalHtml.split("class=\"list\">");
        String resultHtml = splitHtml[0] + "class=\"list\">" + memoList + splitHtml[1];
        return resultHtml.getBytes();
    }
}
