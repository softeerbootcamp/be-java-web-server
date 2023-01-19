package was.view;

import db.Database;
import db.SessionStorage;
import model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewResolver {
    public static byte[] makeDynamicHtml(String userId, String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if (line.contains("class=\"dropdown-toggle\"")) {
                    sb.append("<a>" + userId + "님</a></li><li>");
                }
                sb.append(line);
            }
            return sb.toString().getBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] makeListHtml(String userId, String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if (line.contains("class=\"dropdown-toggle\"")) {
                    sb.append("<a>" + userId + "님</a></li><li>");
                }
                int no = 0;
                sb.append(line);
                if (line.contains("<tbody>")) {
                    for (User user : Database.findAll()) {
                        sb.append("<tr><th scope=\"row\">" + (++no) +
                                "</th> <td>" + user.getUserId() +
                                "</td> <td>" + user.getName() +
                                "</td> <td>" + user.getEmail() +
                                "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
                    }
                }
            }
            return sb.toString().getBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
