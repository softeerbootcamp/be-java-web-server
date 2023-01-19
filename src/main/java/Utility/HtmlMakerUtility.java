package Utility;

import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlMakerUtility {

    public static String userListRows(List<User> users){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(User user : users){
            sb.append(userListRow(count++, user.getUserId(), user.getName(), user.getEmail()));
        }

        return sb.toString();
    }

    public static String userListRow(int number, String userId, String username, String email){
        String message = "<tr>\n" +
                "                    <th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "                </tr>\n";
        message = String.format(message, number, userId, username, email);
        return message;
    }

    public static Map<String, String> getDefaultTemplate(String username){
        if(username.equals(User.GUEST.getName()))
            return new HashMap<>(){{
                put("loginForm", loginButton());
                put("registerForm", createButton());
            }};
        return new HashMap<>(){{
            put("name", username);
            put("logoutForm", logoutButton());
        }};
    }
    public static String loginButton (){
        return "<li><a href=\"../user/login\" role=\"button\">로그인</a></li>";
    }

    public static String logoutButton(){
        return " <li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>";
    }

    public static String createButton(){
        return "<li><a href=\"../user/form\" role=\"button\">회원가입</a></li>";
    }


}
