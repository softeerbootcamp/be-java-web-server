package http.response;

import db.MemoRepository;
import model.Memo;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;

public class DynamicResolver {

    public static byte[] showUserName(File file, String userName) throws Exception {
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
            if (line.contains("<ul class=\"list\">")) {
                sb.append(line).append(System.lineSeparator());
                appendMemoList(sb);
                continue;
            }
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().getBytes();
    }

    public static byte[] showUserList(File file, Collection<User> users, String userName) throws Exception {
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
            if (line.contains("<ul class=\"list\">")) {
                appendMemoList(sb);
                continue;
            }
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().getBytes();
    }

    public static byte[] hideLogoutButton(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("로그아웃")) {
                continue;
            }
            if (line.contains("<ul class=\"list\">")) {
                appendMemoList(sb);
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

    private static void appendMemoList(StringBuilder sb) throws Exception {
        Collection<Memo> memos = MemoRepository.findAll();
        sb.append("<ul class=\"list\">").append(System.lineSeparator());
        for (Memo memo : memos) {
            sb.append("<li><div class=\"wrap\"><div class=\"main\">");
            sb.append("<h4>").append(memo.getContent()).append("</h4>");
            sb.append("<div class=\"auth-info\">");
            sb.append("<span class=\"time\">").append(memo.getCreatedAt()).append("</span>");
            sb.append("<span class=\"tag\">").append(memo.getUserName()).append("</span>");
            sb.append("</div></div></div></li>");
        }
    }
}
