package was.view;

import db.JdbcTemplate;
import model.User;

public class ListCallback implements BufferedReaderCallback{
    private int no;

    public ListCallback() {
        this.no = 0;
    }

    @Override
    public String createDynamicHtmlWith(String line) {
        if (line.contains("<tbody>")) {
            StringBuilder sb = new StringBuilder();
            sb.append(line);
            for (User user : JdbcTemplate.getInstance().findAllUser()) {
                sb.append("<tr><th scope=\"row\">" + (++no) +
                        "</th> <td>" + user.getUserId() +
                        "</td> <td>" + user.getName() +
                        "</td> <td>" + user.getEmail() +
                        "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
            }
            return sb.toString();
        }
        return line;
    }
}
