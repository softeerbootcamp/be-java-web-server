package view;

import model.User;
import util.FileUtils;

import java.util.Collection;

public class UserListView {

    public static String render(Collection<User> users, String name) {
        byte[] file = FileUtils.loadFile("/user/list.html");

        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (User user : users) {
            sb.append("<tr>\n");
            sb.append(String.format("<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>", idx++, user.getUserId(), user.getName(), user.getEmail()));
            sb.append("</tr>\n");
        }

        String userName = "<li><a role=\"button\">사용자 이름: " + name + "</a></li>";

        String htmlFile = new String(file);
        htmlFile = htmlFile.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>", userName);
        htmlFile = htmlFile.replace("<li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>", "");

        String[] split = htmlFile.split("<tbody>", 2);
        return split[0] + "<tbody>" + sb + split[1];
    }
}
