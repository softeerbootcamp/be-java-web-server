package controller;

import db.Database;
import model.User;
import utils.FileIoUtils;
import webserver.*;
import java.util.Collection;

public class UserListController implements Controller{

    public static final String USER_LIST_URL = "/user/list";

    private static final String COOKIE_SESSION_KEY = "sid";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        System.out.println("UserListController doGet");
        String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
        System.out.println("session key: "+sessionKey);
        // 쿠키 값이 유효한 경우 == 로그인 된 경우
        if (HttpSessionManager.getSession(sessionKey) != null) {
            ContentType contentType = ContentType.HTML;
            response.setStatus(HttpStatus.OK);
            response.setContentType(contentType);
            System.out.println(Database.findAll());
            byte[] body = getTable(Database.findAll()).getBytes();
            response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
            response.setBody(body);
            return;
        }

        //로그인하지 않은 상태일 경우 로그인 페이지(login.html)로 이동
        response.redirect("/user/login.html");
    }

    private String getTable(Collection<User> users) {
        int idx = 1;

        StringBuilder content = new StringBuilder();

        for (User user: users) {
            content.append("<tr><th scope=\"row\">")
                    .append(idx++)
                    .append("</th> <td>")
                    .append(user.getUserId())
                    .append("</td> <td>")
                    .append(user.getName())
                    .append("</td> <td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }

        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/user/list.html");
        String html = new String(bytes);
        String[] split = html.split("<tbody>", 2);
        return split[0] + "<tbody>" + content + split[1];
    }
}
