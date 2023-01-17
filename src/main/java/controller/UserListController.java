package controller;

import db.Database;
import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import util.ResourceUtils;

import java.util.Collection;

public class UserListController implements AuthController {
    public static final String PATH = "/user/list";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setBody(getTable(Database.findAll()).getBytes());
        response.addHeader("Content-Type", ContentType.TEXT_HTML.getType());

    }

    private String getTable(Collection<User> users) {
        int idx = 1;

        StringBuilder content = new StringBuilder();

        for (User user: users) {
            content.append("<tr><th scope=\"row\">")
                    .append(idx)
                    .append("</th> <td>")
                    .append(user.getUserId())
                    .append("</td> <td>")
                    .append(user.getName())
                    .append("</td> <td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }

        byte[] bytes = ResourceUtils.loadFileFromClasspath("/user/list.html");
        String html = new String(bytes);
        String[] split = html.split("<tbody>", 2);
        return split[0] + "<tbody>" + content + split[1];
    }

}
