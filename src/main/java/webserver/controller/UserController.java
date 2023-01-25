package webserver.controller;

import db.UserDatabase;
import model.User;
import model.UserDto;
import webserver.Service.UserService;
import webserver.annotation.ControllerInfo;
import webserver.annotation.RequestBody;
import webserver.annotation.RequestParam;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.security.SecurityContext;
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
    public void userCreate(UserDto newUser, Response response, ModelAndView mv) {
        userService.addUser(newUser);
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('계정 생성이 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @ControllerInfo(path = "/user/login", methodName = "userLogin", queryStr = {"userId", "password"}, method = RequestMethod.POST)
    public void userLogin(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv) throws  HttpRequestException{
        String userId = userService.login(queryStrs.get("userId"), queryStrs.get("password"));
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('로그인이 완료되었습니다.');</script>").getBytes(), ContentType.TEXT_HTML);
        response.addCookieOnHeader(HttpSessionUtils.generateSession(userId).toString());
    }

    @ControllerInfo(path = "/user/logout", methodName = "userLogout", method = RequestMethod.GET)
    public void userLogout(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv){
        SecurityContext.clearContext();
        mv.setViewPath("redirect:/index.html");
        response.ok(StatusCodes.OK, ("<script>alert('로그아웃 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
        response.addCookieOnHeader(HttpSessionUtils.cookieInvalidation(mv.getViewModel().get("session-id").toString()));
    }

    @ControllerInfo(path = "/user/list", methodName = "userList", method = RequestMethod.GET)
    public void userList(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv) {
        mv.setViewPath("/user/list.html");
        mv.addViewModel("user", UserDatabase.findAll());
    }

    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
    }
}
