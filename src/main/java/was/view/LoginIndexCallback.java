package was.view;

import db.BoardDatabase;
import model.Board;
import java.util.Collection;

public class LoginIndexCallback implements BufferedReaderCallback {
    @Override
    public String createDynamicHtmlWith(String line) {
        if (line.contains("<ul class=\"list\">")) {
            StringBuilder sb = new StringBuilder();
            Collection<Board> collection = BoardDatabase.findAll();
            for (Board board : collection) {
                sb.append("\n" + "<li>\n" +
                        "                  <div class=\"wrap\">\n" +
                        "                      <div class=\"main\">\n" +
                        "                          <strong class=\"subject\">\n" +
                        "                              <a href=\"./qna/show.html\">" + board.getTitle() + "</a>\n" +
                        "                          </strong>\n" +
                        "                          <div class=\"auth-info\">\n" +
                        "                              <i class=\"icon-add-comment\"></i>\n" +
                        "                              <span class=\"time\">2016-01-15 18:47</span>\n" +
                        "                              <a href=\"./user/profile.html\" class=\"author\">" + board.getWriter() + "</a>\n" +
                        "                          </div>\n" +
                        "                          <div class=\"reply\" title=\"댓글\">\n" +
                        "                              <i class=\"icon-reply\"></i>\n" +
                        "                              <span class=\"point\">0</span>\n" +
                        "                          </div>\n" +
                        "                      </div>\n" +
                        "                  </div>\n" +
                        "              </li>");
            }
            return line + sb.toString();
        }
        return line;
    }
}
