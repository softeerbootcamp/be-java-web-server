package controller;

import db.Database;
import model.User;
import session.HttpSessions;
import utils.StringBuilderUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DynamicController {
    public static byte[] dynamicIndexHtml(byte[] body,String sid){
        byte[] manualBody;
        String stringBody;
        stringBody = StringBuilderUtils.byteArrayToString(body);
        User user = Database.findUserById(HttpSessions.findUserIdBySid(sid));
        stringBody = stringBody.replace("role=\"button\">로그인",StringBuilderUtils.userNameButtonBuilder(user));
        manualBody = StringBuilderUtils.stringToByteArray(stringBody);
        return manualBody;
    }
    public static byte[] dynamicListHtml(byte[] body){
        byte[] manualBody;
        String stringBody;
        stringBody = StringBuilderUtils.byteArrayToString(body);
        Collection<User> allUser = Database.findAll();
        stringBody = stringBody.replace("              </tbody>",StringBuilderUtils.userListStringBuilder(allUser)+"              </tbody>");
        manualBody = StringBuilderUtils.stringToByteArray(stringBody);
        return manualBody;
    }

}
