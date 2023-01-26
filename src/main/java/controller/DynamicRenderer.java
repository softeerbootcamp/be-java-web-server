package controller;

import db.UserDatabase;
import model.User;
import session.HttpSessions;
import utils.StringBuilderUtils;

import java.sql.SQLException;
import java.util.Collection;

public class DynamicRenderer {
    // 로그인 버튼 -> 유저 이름으로
    public static byte[] dynamicIndex_LoginBtnToUserBtn(byte[] body, String sid) throws SQLException {
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        User user = UserDatabase.findUserById(HttpSessions.findUserIdBySid(sid));
        stringBody = stringBody.replace("<a href=\"user/login.html\" role=\"button\">로그인</a>", StringBuilderUtils.userNameButtonBuilder(user));
        return StringBuilderUtils.stringToByteArray(stringBody);
    }

    public static byte[] dynamicIndex_LogoutBtnOn(byte[] body) {
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        stringBody = stringBody.replace("<!--                <li><a href=\"#\" role=\"button\">로그아웃</a></li>-->\n" +
                "<!--                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>-->", StringBuilderUtils.userLogoutButtonOnBuilder());
        return StringBuilderUtils.stringToByteArray(stringBody);
    }
    public static byte[] dynamicIndex_LogoutBtnOff(byte[] body) {
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        stringBody = stringBody.replace("<li><a href=\"#\" role=\"button\">로그아웃</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>", StringBuilderUtils.userLogoutButtonOffBuilder());
        return StringBuilderUtils.stringToByteArray(stringBody);
    }
    public static byte[] dynamicIndex_SignUpBtnOff(byte[] body){
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        stringBody = stringBody.replace("                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>\n", StringBuilderUtils.signUpButtonHideBuilder());
        return StringBuilderUtils.stringToByteArray(stringBody);
    }
    // 게시판 전체 출력
    public static byte[] dynamicIndex_ViewAllBoard(byte[] body) throws SQLException {
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        stringBody = stringBody.replace("<ul class=\"list\">", "<ul class=\"list\">\n" + StringBuilderUtils.viewAllBoardBuilder());
        return StringBuilderUtils.stringToByteArray(stringBody);
    }

    public static byte[] dynamicListHtml(byte[] body) throws SQLException {
        // todo : db 에서 들고오기.
        String stringBody = StringBuilderUtils.byteArrayToString(body);
        Collection<User> allUser = UserDatabase.findAll();
        stringBody = stringBody.replace("              </tbody>", StringBuilderUtils.userListStringBuilder(allUser) + "              </tbody>");
        return StringBuilderUtils.stringToByteArray(stringBody);
    }

}
