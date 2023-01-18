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
        User authUser = (User) data.getAttribute("authUser");
        List<User> users = new ArrayList<>();

        for (Object o : (Collection<?>) data.getAttribute("users")) {
            users.add((User) o);
        }

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

        byte[] bytes = ResourceUtils.loadFileFromClasspath("/user/list.html");
        String html = new String(bytes);
        String[] split = html.split("<tbody>", 2);
        response.setBody((split[0] + "<tbody>" + content + split[1]).getBytes());
        response.addHeader("Content-Type", ContentType.TEXT_HTML.getType());
    }
}
