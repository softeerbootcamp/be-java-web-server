package util;

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
}
