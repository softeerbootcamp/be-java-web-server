package view;

import model.Memo;
import util.FileUtils;

import java.io.IOException;
import java.util.Collection;

public class HomeView {

    public static String render(String url, Collection<Memo> memos, String body) throws IOException {

        StringBuilder memoList = new StringBuilder();
        int idx = 1;
        for (Memo memo : memos) {
            memoList.append("<li>")
                    .append("<div class=\"wrap\">")
                    .append("<div class=\"main\">")
                    .append("<strong class=\"subject\">")
                    .append(memo.getContent())
                    .append("</strong>")
                    .append("<div class=\"auth-info\">")
                    .append("<i class=\"icon-add-comment\"></i>\n" +
                            "<span class=\"time\">" + memo.getCreatedAt() + "</span>\n" +
                            "<a href=\"./user/profile.html\" class=\"author\">" + memo.getWriter()+ "</a>\n" +
                            "</div>")
                    .append("<div class=\"reply\" title=\"댓글\">\n" +
                            "                              <i class=\"icon-reply\"></i>\n" +
                            "                              <span class=\"point\">" + idx++ + "</span>\n" +
                            "                          </div>")
                    .append("</div></div></li>");
        }

        if (body.length() == 0) {
            byte[] file = FileUtils.loadFile(url);
            body = new String(file);
        }

        return body.replace("<!--메모 목록 구현-->", memoList);
    }
}
