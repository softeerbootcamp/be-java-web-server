package was.view;

import db.Database;
import model.User;
import service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginListCallback implements BufferedReaderCallback{
    private int no;

    public LoginListCallback() {
        this.no = 0;
    }

    @Override
    public String createDynamicHtmlWith(String line) {
        if (line.contains("<tbody>")) {
            for (User user : Database.findAll()) {
                return line + "<tr><th scope=\"row\">" + (++no) +
                        "</th> <td>" + user.getUserId() +
                        "</td> <td>" + user.getName() +
                        "</td> <td>" + user.getEmail() +
                        "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>";
            }
        }
        return line;
    }
}
