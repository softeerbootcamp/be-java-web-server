package util;

import model.Qna;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class HtmlBuildUtil {
    private static final Logger logger = LoggerFactory.getLogger(HtmlBuildUtil.class);

    public static String buildUserList(Collection<User> users) {
        int idx = 1;
        String listHtml = new String(HttpResponseUtil.generateBytesBody("/user/list.html"));
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
    public static String buildQnaList(List<Qna> qnas) {
        String indexHtml = new String(HttpResponseUtil.generateBytesBody("/index.html"));
        StringBuilder content = new StringBuilder();
        for (Qna qna : qnas) {
            content.append("<li><div class=\"wrap\"><div class=\"main\"><strong class=\"subject\"><a href=\"./qna/show/")
                    .append(qna.getId())
                    .append("\">")
                    .append(qna.getSubject())
                    .append("</a></strong><div style=\"display: flex; gap:12px\" class=\"auth-info\"> <i class=\"icon-add-comment\"></i><span class=\"time\">")
                    .append(qna.getDate())
                    .append("</span><a href=\"./user/profile.html\" class=\"author\">")
                    .append(qna.getName())
                    .append("</a></div><div class=\"reply\" title=\"댓글\"><i class=\"icon-reply\"></i><span class=\"point\">")
                    .append(qna.getId())
                    .append("</span></div></div></div></li>\n");
        }
        return indexHtml.replace("<!--qna list location-->", content.toString());
    }
    public static String buildQnaShow(Qna qna) {
        String showHtml = new String(HttpResponseUtil.generateBytesBody("/qna/show.html"));

        return showHtml.replace("<!--name location-->", qna.getName())
                .replace("<!--title location-->", qna.getSubject())
                .replace("<!--time location-->", qna.getDate())
                .replace("<!--content location-->", qna.getContent());

    }
    public static byte[] buildHtml(String path, User user) {
        byte[] body = HttpResponseUtil.generateBytesBody(path);
        logger.debug("withoutLogoutWithUserName");

        if (FileIoUtil.mappingDirectoryPath(path).toString().contains("static")) {
            return body;
        }
        return withoutLoginWithUserName(new String(body), path, user);
    }

    public static byte[] withoutLoginWithUserName(String html, String path, User user) {
        logger.debug("html Template");
        if (path.startsWith("/qna") || path.startsWith("/user"))
            html = html.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>", "");
        html = html.replace("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>", "");
        return html.replace("<!--username location-->", "<li><a> " + user.getName() + " </a></li>").getBytes();
    }
}
