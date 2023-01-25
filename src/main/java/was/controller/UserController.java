package was.controller;

import db.SessionStorage;
import enums.HttpMethod;
import model.User;
import service.UserService;
import was.annotation.Auth;
import was.annotation.RequestMapping;
import webserver.session.Session;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.Map;
import java.util.UUID;

public class UserController implements Controller{
    private static UserController userController;
    private final UserService userService = UserService.getInstance();
    private UserController() {
    }
    public static UserController getInstance() {
        if (userController == null) {
            return new UserController();
        }
        return userController;
    }
    @RequestMapping(method = HttpMethod.POST, value = "/user/create")
    public HttpResponse create(HttpRequest httpRequest) {
        userService.addUser(httpRequest.getBody());

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.sendRedirect("/index.html");
        return httpResponse;
    }

    @RequestMapping(method = HttpMethod.POST, value = "/user/login")
    public HttpResponse login(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getBody();
        User user = new User(body.get("userId"), body.get("password"));

        HttpResponse httpResponse = new HttpResponse();
        String path = httpResponse.findPath("/index.html");
        if (userService.login(user)) {
            UUID sid = SessionStorage.addSession(Session.createSessionWith(user));
            httpResponse.sendCookieWithRedirect(sid, "/index.html");
            return httpResponse;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
        return httpResponse;
    }
    @Auth
    @RequestMapping(method = HttpMethod.GET, value = "/user/list")
    public HttpResponse showList(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        UUID sessionId = httpRequest.getSessionId().get();
        String path = httpResponse.findPath(httpRequest.getRequestLine().getUrl() + ".html");
        httpResponse.forwardListPage(path, sessionId);
        return httpResponse;
    }
    @Auth
    @RequestMapping(method =  HttpMethod.GET, value = "/user/logout")
    public HttpResponse logOut(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        UUID sessionId = httpRequest.getSessionId().get();
        SessionStorage.findSessionBy(sessionId).invalidate();
        httpResponse.sendRedirect("/index.html");
        return httpResponse;
    }
}
