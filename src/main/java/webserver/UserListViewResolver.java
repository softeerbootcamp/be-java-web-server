package webserver;

import model.User;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserListViewResolver {


    private static String getTableOfUser(Object userObjList){
        String rawStr = "<tr> <th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n </tr>";
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        ArrayList<User> userList = ArrayList.class.cast(userObjList);
        userList.forEach(user-> sb.append(String.format(rawStr, count.incrementAndGet(), user.getUserId(), user.getName(), user.getEmail())));

        return sb.toString();
    }

    public static void makeUserListView(ModelAndView mv, Response res) throws IOException {
        String path = mv.getViewPath();
        String userTableScript = getTableOfUser(mv.getViewModel().get("user"));
        StaticResourceFinder.staticFileResolver(path)
                .ifPresent(fileAsBytes -> {
                    String fileContent = new String(fileAsBytes);
                    String newContent = fileContent.replaceAll("(?is)<tbody.+?/tbody>", "<tbody> " + userTableScript + " </tbody>");
                    res.addHeaderAndBody(StatusCodes.OK, newContent.getBytes(), StaticResourceFinder.getExtension(path));});//if not
    }
}
