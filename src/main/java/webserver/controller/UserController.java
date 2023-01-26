package webserver.controller;

import db.tmpl.UserDatabase;
import model.User;
import model.dto.UserDto;
import webserver.Service.UserService;
import webserver.annotation.ControllerInfo;
import webserver.annotation.PathVariable;
import webserver.annotation.RequestBody;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.security.SecurityContext;
import webserver.utils.HttpSessionUtils;

import java.util.Map;

public class UserController implements Controller {

    private UserController (){}

    public static UserController getInstance(){
        return UserController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{  //Singleton
        private static final UserController INSTANCE = new UserController();
    }

    private UserService userService = UserService.getInstance();
    private HttpSessionUtils httpSessionUtils = HttpSessionUtils.getInstance();
    private UserDatabase userDatabase;

    public void setUserDatabase(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    @ControllerInfo(path = "/user/create", methodName = "userCreate", queryStr = {"userId", "password", "name", "email"}, method = RequestMethod.POST)
    public void userCreate(UserDto newUser, Response response, ModelAndView mv) {
        userService.addUser(newUser);
        mv.setViewPath("redirect:/");
        response.addHeaderAndBody(StatusCodes.OK, ("<script>alert('계정 생성이 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
    }

    @ControllerInfo(path = "/user/login", methodName = "userLogin", queryStr = {"userId", "password"}, method = RequestMethod.POST)
    public void userLogin(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv) throws  HttpRequestException{
        User user = userService.login(queryStrs.get("userId"), queryStrs.get("password"));
        mv.setViewPath("redirect:/");
        response.addHeaderAndBody(StatusCodes.OK, ("<script>alert('로그인이 완료되었습니다.');</script>").getBytes(), ContentType.TEXT_HTML);
        response.addCookieOnHeader(httpSessionUtils.generateSession(user.getUserId()).toString());
    }

    @ControllerInfo(path = "/user/logout", methodName = "userLogout", method = RequestMethod.GET)
    public void userLogout(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv){
        SecurityContext.clearContext();
        mv.setViewPath("redirect:/");
        response.addHeaderAndBody(StatusCodes.OK, ("<script>alert('로그아웃 완료되었습니다.'); </script>").getBytes(), ContentType.TEXT_HTML);
        response.addCookieOnHeader(httpSessionUtils.cookieInvalidation(mv.getViewModel().get("session-id").toString()));
    }

    @ControllerInfo(path = "/user/list", methodName = "userList", method = RequestMethod.GET)
    public void userList(@RequestBody Map<String, String> queryStrs, Response response, ModelAndView mv) {
        mv.setViewPath("/user/list.html");
        mv.addViewModel("user", userDatabase.findAll());
    }

    @ControllerInfo(path = "/user/profile/{userId}", methodName = "userProfile", method = RequestMethod.GET)
    public void userProfile(@PathVariable String userId, Response response, ModelAndView mv) {
        mv.setViewPath("/user/profile.html");
        mv.addViewModel("user", userDatabase.findUserById(userId));
    }


    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.getInstance().executeController(getInstance(), req, res, mv);
    }
}
