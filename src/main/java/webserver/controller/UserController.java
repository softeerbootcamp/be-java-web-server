package webserver.controller;

import db.UserDatabase;
import model.User;
import webserver.Service.UserService;
import webserver.annotation.ControllerInfo;
import webserver.domain.ContentType;
import webserver.domain.ModelAndView;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.HttpSessionUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserController implements Controller {

    private UserController (){}

    public static UserController getInstance(){
        return UserController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final UserController INSTANCE = new UserController();
    }

    public UserService userService = UserService.getInstance();


    @ControllerInfo(path = "/user/create", methodName = "userCreate", queryStr = {"userId", "password", "name", "email"}, method = RequestMethod.POST)
    public void userCreate(Map<String, String> queryStrs, Response response, ModelAndView mv) {
        userService.addUser(queryStrs.get("userId"), queryStrs.get("password"), queryStrs.get("name"), queryStrs.get("email"));
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('계정 생성이 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @ControllerInfo(path = "/user/login", methodName = "userLogin", queryStr = {"userId", "password"}, method = RequestMethod.POST)
    public void userLogin(Map<String, String> queryStrs, Response response, ModelAndView mv) throws  HttpRequestException{
        String userId = userService.login(queryStrs.get("userId"), queryStrs.get("password"));
        if(queryStrs.get("Cookie") == null)     //when client got no session for now, generate a new cookie and add it on response header
            response.addCookieOnHeader(HttpSessionUtils.generateSession(userId).toString());
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('로그인이 완료되었습니다.');</script>").getBytes(), ContentType.TEXT_HTML);
    }

    @ControllerInfo(path = "/user/logout", methodName = "userLogout", method = RequestMethod.GET)
    public void userLogout(Map<String, String> queryStrs, Response response, ModelAndView mv){
        String sessionId = queryStrs.get("Cookie");
        HttpSessionUtils.cookieInvalidation(sessionId);
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('로그아웃 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
        response.addCookieOnHeader(HttpSessionUtils.cookieInvalidation(sessionId));
    }

    @ControllerInfo(path = "/user/list", methodName = "userList", method = RequestMethod.GET)
    public void userList(Map<String, String> queryStrs, Response response, ModelAndView mv) throws IOException {
        List<User> allUsers = UserDatabase.findAll();
        mv.setViewPath("/user/list.html");
        mv.addViewModel("user", allUsers);
    }

    @Override
    public void chain(Request req, Response res, ModelAndView mv) throws HttpRequestException {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
    }
}
