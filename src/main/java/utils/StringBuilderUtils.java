package utils;

import model.User;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class StringBuilderUtils {
    private static StringBuilder stringBuilder;
    // 현재 하드코딩으로 javajigi slipp 들어가있어서 임시로 상수 설정. 해당부분 지워도 되면 수정하겠음.
    private static int LIST_START_INDEX = 3;

    public static String byteArrayToString(byte[] body) {
        stringBuilder  = new StringBuilder();
        stringBuilder.append(new String(body));
        return stringBuilder.toString();
    }
    public static byte[] stringToByteArray(String target){
        return target.getBytes();
    }
    public static int getStringIndex(String target){
        return stringBuilder.indexOf(target);
    }
    public static String userListStringBuilder(Collection<User> allUser){
        int index=LIST_START_INDEX;
        stringBuilder = new StringBuilder();
        for (User user:allUser) {
            stringBuilder.append("                <tr>\n");
            stringBuilder.append("                    <th scope=\"row\">");
            stringBuilder.append(index);
            index++;
            stringBuilder.append("</th> <td>"+user.getUserId());
            stringBuilder.append("</th> <td>"+user.getName());
            stringBuilder.append("</th> <td>"+user.getEmail());
            stringBuilder.append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            stringBuilder.append("                </tr>\n");
        }
        return stringBuilder.toString();
    }
    public static String userNameButtonBuilder(User user){
        stringBuilder = new StringBuilder();
        stringBuilder.append("role=\"button\" disabled>");
        stringBuilder.append(user.getName());
        return stringBuilder.toString();
    }
}
