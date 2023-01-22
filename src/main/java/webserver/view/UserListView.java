package webserver.view;

import model.User;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserListView implements View{

    private UserListView (){}

    public static UserListView getInstance(){
        return UserListView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final UserListView INSTANCE = new UserListView();
    }

    public String resourceUrl = "/user/list.html";

    private String getTableOfUser(Object userObjList){     //make html <tbody> tag with userList
        String rawStr = "<tr> <th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n </tr>";
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        ArrayList<User> userList = ArrayList.class.cast(userObjList);
        userList.forEach(user-> sb.append(String.format(rawStr, count.incrementAndGet(), user.getUserId(), user.getName(), user.getEmail())));

        return sb.toString();
    }


    @Override
    public void render(Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException{
        String userTableScript = getTableOfUser(mv.getViewModel().get("user"));
        StaticResourceFinder.staticFileResolver(resourceUrl)
                .ifPresent( fileAsBytes -> {
                    String fileContent = new String(fileAsBytes);
                    fileContent = fileContent.replace(menuBar, "")
                                             .replace("<li id=\"username\"></li>", nameTag)
                                             .replaceAll("(?is)<tbody.+?/tbody>", "<tbody> " + userTableScript + " </tbody>");
                    res.ok(StatusCodes.OK, fileContent.getBytes(), StaticResourceFinder.getExtension(resourceUrl));
                });
    }

}
