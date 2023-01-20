package view;

import model.User;

import java.util.Collection;
import java.util.List;

public class UserRender {
    private static UserRender userRender;

    private UserRender() {}

    public static UserRender getInstance() {
        if (userRender == null) {
            userRender = new UserRender();
        }
        return userRender;
    }

    public byte[] addUserList(byte[] userListHtml, Collection<User> userList) {
        String userListStr = new String(userListHtml);
        String fixedUserListHtml = userListStr.replace("<!--                <tr>-->\n" +
                        "<!--                    <th scope=\"row\">1</th> <td>javajigi</td> <td>자바지기</td> <td>javajigi@sample.net</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>-->\n" +
                        "<!--                </tr>-->",
                getUserListHtml(userList));
        return fixedUserListHtml.getBytes();
    }

    private String getUserListHtml(Collection<User> userList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : userList) {
            stringBuilder.append("<tr>\n");
            stringBuilder.append("<th scope=\"row\">1</th> <td>" + user.getUserId() + "</td>" +
                    " <td>" + user.getName() + "</td> " +
                    "<td>" + user.getEmail() + "</td>" +
                    "<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
        }
        return stringBuilder.toString();
    }
}
