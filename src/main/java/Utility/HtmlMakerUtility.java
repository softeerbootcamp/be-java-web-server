package Utility;

import model.Post;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlMakerUtility {

    private static final String loginButton = "<li><a href=\"../user/login\" role=\"button\">로그인</a></li>";
    private static final String logoutButton = "<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>";
    private static final String createButton = "<li><a href=\"../user/form\" role=\"button\">회원가입</a></li>";
    private static final String userListRow = "<tr><th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>";

    public static String userListRows(List<User> users) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (User user : users) {
            sb.append(String.format(userListRow, count++, user.getUserId(), user.getName(), user.getEmail()));
        }

        return sb.toString();
    }

    public static String postListRows(List<Post> posts) {
        StringBuilder sb = new StringBuilder();
        for (Post post : posts) {
            sb.append(postListRow(post.getPostId(), post.getAuthor(), post.getTitle(), post.getCreatedAt()));
        }
        return sb.toString();
    }

    public static String postListRow(Long postId, String author, String title, String date) {
        String row = String.format(
                "<li>\n" +
                        "    <div class=\"wrap\">\n" +
                        "        <div class=\"main\">\n" +
                        "            <strong class=\"subject\">\n" +
                        "                <a href=\"./qna/show.html\">%s</a>\n" +
                        "            </strong>\n" +
                        "            <div class=\"auth-info\">\n" +
                        "                <i class=\"icon-add-comment\"></i>\n" +
                        "                <span class=\"time\">%s</span>\n" +
                        "                <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                        "            </div>\n" +
                        "            <div class=\"reply\" title=\"댓글\">\n" +
                        "                <i class=\"icon-reply\"></i>\n" +
                        "                <span class=\"point\">%s</span>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</li>", title, date, author, postId);
        return row;
    }


    public static Map<String, String> getDefaultTemplate(String username) {
        if (username.equals(User.GUEST.getName()))
            return new HashMap<>() {{
                put("loginForm", loginButton);
                put("registerForm", createButton);
            }};
        return new HashMap<>() {{
            put("name", username);
            put("logoutForm", logoutButton);
        }};
    }
}
