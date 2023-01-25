package view;

import Request.HttpRequest;
import db.Database;
import model.User;
import util.HtmlBuildUtil;
import util.LoginUtil;

import java.util.Objects;

public class UserListView implements View{
    private static UserListView userListView = null;
    public static UserListView getInstance(){
        if(Objects.isNull(userListView)){
            synchronized (UserListView.class){
                userListView = new UserListView();
            }
        }
        return userListView;
    }
    @Override
    public byte[] render(HttpRequest httpRequest) {
        String html = HtmlBuildUtil.buildUserList(Database.findAll());
        User user = LoginUtil.checkSession(httpRequest);
        byte[] body = HtmlBuildUtil.withoutLoginWithUserName(html, "/user/list.html", user);
        return body;
    }
}
