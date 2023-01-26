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
                    if (line.contains("Posts")) {
                        sb.append(String.format(POSTS_TAG, session.getUserName())).append(System.lineSeparator());
                    }
                    if (line.contains("로그인") || line.contains("회원가입")) {
                        continue;
                    }
                } else if (line.contains("로그아웃")) {
                    continue;
                }
                if (line.contains("%userList%")) {
                    appendUserList(sb);
                    continue;
                }
                if (line.contains("<ul class=\"list\">")) {
                    appendMemoList(sb);
                    continue;
                }
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString().getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void appendUserList(StringBuilder sb) throws Exception {
        int idx = 1;
        Collection<User> users = UserRepository.findAll();
        for (User user : users) {
            sb.append(String.format(USER_LIST_TAG, idx, user.getUserId(), user.getName(), user.getEmail()));
            idx++;
        }
    }

    private static void appendMemoList(StringBuilder sb) throws Exception {
        Collection<Memo> memos = MemoRepository.findAll();
        sb.append("<ul class=\"list\">").append(System.lineSeparator());
        for (Memo memo : memos) {
            sb.append(String.format(MEMO_LIST_TAG, memo.getContent(), memo.getCreatedAt(), memo.getUserName()));
        }
    }
}
