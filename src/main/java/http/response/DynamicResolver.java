package http.response;

import db.MemoRepository;
import db.UserRepository;
import http.session.HttpSession;
import model.Memo;
import model.User;

import java.io.*;
import java.util.Collection;
import java.util.Objects;

public class DynamicResolver {

    private static final String POSTS_TAG = "<li><a>%s</a></li>";
    private static final String USER_LIST_TAG = "<tr><th scope=\"row\">%d</th><td>%s</td><td>%s</td><td>%s</td><td><tr>";
    private static final String MEMO_LIST_TAG = "<li><div class=\"wrap\"><div class=\"main\">" +
                                                "<h4>%s</h4>" +
                                                "<div class=\"auth-info\">" +
                                                "<span class=\"time\">%s</span>" +
                                                "<span class=\"tag\">%s</span>" +
                                                "</div></div></div></li>";

    public static byte[] createDynamicHtml(File file, HttpSession session) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (Objects.nonNull(session)) {
                    line = loginDynamicHtml(line, session.getUserName());
                } else if (line.contains("로그아웃")) {
                    line = "";
                }
                if (line.contains("%userList%")) {
                    line = appendUserList();
                }
                if (line.contains("<ul class=\"list\">")) {
                    line = appendMemoList();
                }
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString().getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String loginDynamicHtml(String line, String userName) {
        if (line.contains("Posts")) {
            return line + String.format(POSTS_TAG, userName) + System.lineSeparator();
        }
        if (line.contains("로그인") || line.contains("회원가입")) {
            return "";
        }
        return line;
    }

    private static String appendUserList() {
        int idx = 1;
        Collection<User> users = UserRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(String.format(USER_LIST_TAG, idx, user.getUserId(), user.getName(), user.getEmail()));
            idx++;
        }
        return sb.toString();
    }

    private static String appendMemoList() {
        Collection<Memo> memos = MemoRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<ul class=\"list\">").append(System.lineSeparator());
        for (Memo memo : memos) {
            sb.append(String.format(MEMO_LIST_TAG, memo.getContent(), memo.getCreatedAt(), memo.getUserName()));
        }
        return sb.toString();
    }
}
