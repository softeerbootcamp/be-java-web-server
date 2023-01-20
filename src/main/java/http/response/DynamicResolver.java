package http.response;

import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class DynamicResolver {

    public static byte[] showUserName(File file, String userName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("로그인") || line.contains("회원가입")) {
                continue;
            }
            if (line.contains("Posts")) {
                sb.append("<li><a>").append(userName).append("</a></li>").append(System.lineSeparator());
            }
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().getBytes();
    }

    public static byte[] showUserList(File file, Collection<User> users, String userName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("로그인") || line.contains("회원가입")) {
                continue;
            }
            if (line.contains("Posts")) {
                sb.append("<li><a>").append(userName).append("</a></li>").append(System.lineSeparator());
            }
            if (line.contains("%userList%")) {
                appendUserList(sb, users);
                continue;
            }
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().getBytes();
    }

    public static byte[] hideLogoutButton(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("로그아웃")) {
                continue;
            }
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().getBytes();
    }

    private static void appendUserList(StringBuilder sb, Collection<User> users) {
        int idx = 1;
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(idx).append("</th>");
            sb.append("<td>").append(user.getUserId()).append("</td>");
            sb.append("<td>").append(user.getName()).append("</td>");
            sb.append("<td>").append(user.getEmail()).append("</td>");
            sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            sb.append("<tr>");
            idx++;
        }
    }
}
