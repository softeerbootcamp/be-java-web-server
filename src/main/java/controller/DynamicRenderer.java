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
        byte[] manualBody;
        String stringBody;
        stringBody = StringBuilderUtils.byteArrayToString(body);
        User user = UserDatabase.findUserById(HttpSessions.findUserIdBySid(sid));
        stringBody = stringBody.replace("role=\"button\">로그인",StringBuilderUtils.userNameButtonBuilder(user));
        manualBody = StringBuilderUtils.stringToByteArray(stringBody);
        return manualBody;
    }
    // 게시판 전체 출력
    public static byte[] dynamicIndex_ViewAllBoard(byte[] body) throws SQLException {
        byte[] manualBody;
        String stringBody;
        stringBody = StringBuilderUtils.byteArrayToString(body);
        stringBody = stringBody.replace("<ul class=\"list\">","<ul class=\"list\">\n"+StringBuilderUtils.viewAllBoardBuilder());
        manualBody = StringBuilderUtils.stringToByteArray(stringBody);
        return manualBody;
    }
    public static byte[] dynamicListHtml(byte[] body){
        byte[] manualBody;
        String stringBody;
        // todo : db 에서 들고오기.
        stringBody = StringBuilderUtils.byteArrayToString(body);
        Collection<User> allUser = UserDatabase.findAll();
        stringBody = stringBody.replace("              </tbody>",StringBuilderUtils.userListStringBuilder(allUser)+"              </tbody>");
        manualBody = StringBuilderUtils.stringToByteArray(stringBody);
        return manualBody;
    }

}
