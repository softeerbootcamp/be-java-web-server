package view;

import model.User;
import util.FileUtils;

import java.util.Collection;

public class UserListView {

    public static String render(Collection<User> users) {
        byte[] file = FileUtils.loadFile("/user/list.html");

        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (User user : users) {
            sb.append("<tr>\n");
            sb.append(String.format("<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>", idx++, user.getUserId(), user.getName(), user.getEmail()));
            sb.append("</tr>\n");
        }

        String htmlFile = new String(file);

        String[] split = htmlFile.split("<tbody>", 2);
        return split[0] + "<tbody>" + sb + split[1];
    }
}
