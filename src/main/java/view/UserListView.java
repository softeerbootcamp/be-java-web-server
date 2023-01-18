package view;

import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import util.ResourceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserListView implements View {

    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        String page = new String(ResourceUtils.loadFileFromClasspath("/user/list.html"));
        User authUser = (User) data.getAttribute("authUser");

        String name = "<!--<li>name</li>-->";
        String replaceName = "<li>" + authUser.getName() + "</li>";
        page = page.replace(name, replaceName);

        List<User> users = new ArrayList<>();
        for (Object o : (Collection<?>) data.getAttribute("users")) {
            users.add((User) o);
        }
        String contentOfUsers = contentOfUsers(users, authUser);
        String target = "<!--userList-->";
        page = page.replace(target, contentOfUsers);

        response.setBody(page.getBytes());
        response.addHeader("Content-Type", ContentType.TEXT_HTML.getType());
    }

    private String contentOfUsers(List<User> users, User authUser) {
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
                    .append("</td><td>");
            if (user.getUserId().equals(authUser.getUserId())) {
                content.append("<a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a>");
            }
            content.append("</td></tr>");
        }

        return content.toString();
    }
}
