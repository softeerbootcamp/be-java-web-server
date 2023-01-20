package filesystem;

import model.User;

import java.util.List;

public class HtmlGenerator {

    public static String getUserListLi(List<User> users) {
        StringBuilder html = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            html.append("<tr>" + "<th scope='row'>").append(i).append(1).append("</th>").append("<td>")
                    .append(users.get(i).getUserId()).append("</td>").append("<td>").append(users.get(i).getName())
                    .append("</td>").append("<td>").append(users.get(i).getEmail()).append("</td>")
                    .append("<td><a href='#' class='btn btn-success' role='button'>수정</a></td>")
                    .append("</tr>");
        }
        return html.toString();
    }

    public static String getUserAnchor(User user) {
        return "<li><a href='#' role='button'>" + user.getName() + "</a></li> " +
                "<li><a href='#' role='button'>로그아웃</a></li>" +
                "<li><a href='#' role='button'>개인정보수정</a></li>";
    }

    public static String getNotFoundHTML() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "not found\n" +
                "</body>\n" +
                "</html>";
    }
}
