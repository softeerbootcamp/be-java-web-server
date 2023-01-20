package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class HtmlBuildUtil {
    private static final Logger logger = LoggerFactory.getLogger(HtmlBuildUtil.class);

    public static String userList(Collection<User> users) {
        int idx = 1;
        String listHtml = new String(HttpResponseUtil.generateBody("/user/list.html"));
        StringBuilder content = new StringBuilder();
        for (User user : users) {
            content.append("<tr><th scope=\"row\">")
                    .append(idx++)
                    .append("</th> <td>")
                    .append(user.getUserId())
                    .append("</td> <td>")
                    .append(user.getName())
                    .append("</td> <td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }
        return listHtml.replace("<!-- user list location -->", content.toString());
    }

    public static byte[] buildHtml(String path, User user) {
        byte[] body = HttpResponseUtil.generateBody(path);
        logger.debug("withoutLogoutWithUserName");

        if (FileIoUtil.mappingDirectoryPath(path).toString().contains("static")) {
            return body;
        }
        return withoutLogoutWithUserName(new String(body), path, user);
    }

    public static byte[] withoutLogoutWithUserName(String html, String path, User user) {
        logger.debug("html Template");
        if (path.startsWith("/qna") || path.startsWith("/user"))
            html = html.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>", "");
        html = html.replace("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>", "");
        return html.replace("<!--username location-->", "<li><a> " + user.getName() + " </a></li>").getBytes();
    }
}
