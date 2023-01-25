package was.view;

import db.Database;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginListCallback implements BufferedReaderCallback{
    private int no;

    public LoginListCallback() {
        this.no = 0;
    }

    @Override
    public String createDynamicHtmlWith(String line, String userId) {
        if (line.contains("로그인") || line.contains("회원가입")) {
            return "";
        }
        if (line.contains("class=\"dropdown-toggle\"")) {
            return "<a>" + userId + "님</a></li><li>" + line;
        }
        if (line.contains("<tbody>")) {
            for (User user : Database.findAll()) {
                return line + "<tr><th scope=\"row\">" + (++no) +
                        "</th> <td>" + user.getUserId() +
                        "</td> <td>" + user.getName() +
                        "</td> <td>" + user.getEmail() +
                        "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>";
            }
        }
        if (line.contains("Posts")) {
            return line + "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>" +
                    "<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>";
        }
        return line;
    }
}
