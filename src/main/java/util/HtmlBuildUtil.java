package util;

import model.User;

import java.util.Collection;

public class HtmlBuildUtil {
    public static String UserList(Collection<User> users) {
        int idx = 1;
        String listHtml = HttpResponseUtil.generateBody("/user/list.html").toString();
        String[] htmls = listHtml.split("<tbody>", 2);
        StringBuilder content = new StringBuilder();

        content.append(htmls[0]);
        content.append("<tbody>");
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
        content.append(htmls[1]);

        return content.toString();
    }

}
