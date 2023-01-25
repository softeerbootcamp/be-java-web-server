package util;

import model.domain.Memo;
import model.domain.User;

import java.util.Collection;
import java.util.List;

public class HtmlEditor {
    private static List<String> removeHrefList = List.of(
            "href=\"../user/login.html\"",
            "href=\"user/login.html\"",
            "href=\"../user/form.html\"",
            "href=\"user/form.html\""
    );

    public static byte[] removeHref(byte[] body){
        String bodyStr = new String(body);
        for (String rmHref : removeHrefList){
            bodyStr = bodyStr.replace(rmHref," ");
        }
        return bodyStr.getBytes();
    }

    public static byte[] editNavbar(byte[] body, String userID){
        String bodyStr = new String(body);
        bodyStr = bodyStr.replace("로그인",userID);
        bodyStr = bodyStr.replace("회원가입","님, 환영합니다.");
        return bodyStr.getBytes();
    }

    public static byte[] appendUserList(byte[] body, Collection<User> users){
        int row = 1;
        String bodyStr = new String(body);
        StringBuilder userList = new StringBuilder("<tbody>\n");
        for (User user: users){
            userList.append("<tr>\n<th scope=\"row\">").append(row++).append("</th> <td>")
                    .append(user.getUserId()).append("</td> <td>").append(user.getName()).append("</td> <td>")
                    .append(user.getEmail()).append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>\n");
        }
        bodyStr = bodyStr.replace("<tbody>",userList);
        return bodyStr.getBytes();
    }

    public static byte[] appendMemoList(byte[] body, Collection<Memo> memos){
        String bodyStr = new String(body);
        StringBuilder memoList = new StringBuilder("<ul class=\"list\">");
        //todo: memoList 동적으로 보이도록 html 편집 코드 작성
        return null;
    }

}
