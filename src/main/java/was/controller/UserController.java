package was.controller;

import db.SessionStorage;
import enums.HttpMethod;
import model.User;
import service.UserService;
import was.annotation.RequestMapping;
import was.view.ViewResolver;
import webserver.session.Session;
import was.annotation.PostMapping;
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
    public HttpResponseMessage create(HttpRequest httpRequest) {
        userService.addUser(httpRequest.getBody());

        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }

    @RequestMapping(method = HttpMethod.POST, value = "/user/login")
    public HttpResponseMessage login(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getBody();
        User user = new User(body.get("userId"), body.get("password"));

        HttpResponse httpResponse = new HttpResponse();
        String path = httpResponse.findPath("/index.html");
        if (userService.login(user)) {
            UUID sid = SessionStorage.addSession(Session.createSessionWith(user));
            return new HttpResponseMessage(httpResponse.sendCookieWithRedirect(sid, "/index.html"), httpResponse.getBody());
        }
        return new HttpResponseMessage(httpResponse.unauthorized(), httpResponse.getBody());
    }
    @RequestMapping(method = HttpMethod.GET, value = "/user/list")
    public HttpResponseMessage showList(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        UUID sessionId = httpRequest.getSessionId().orElse(null);
        if (sessionId == null || !SessionStorage.isExist(sessionId)) {
            return new HttpResponseMessage(httpResponse.sendRedirect("/user/login.html"), httpResponse.getBody());
        }
        String userId = SessionStorage.findSessionBy(sessionId).getUserId();
        String path = httpResponse.findPath(httpRequest.getRequestLine().getUrl() + ".html");
        System.out.println("path = " + path);
        System.out.println("userId = " + userId);
        return new HttpResponseMessage(httpResponse.forwardListPageHeaderMessage(userId, path), httpResponse.getBody());
    }

    @RequestMapping(method =  HttpMethod.GET, value = "/user/logout")
    public HttpResponseMessage logOut(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        UUID sessionId = httpRequest.getSessionId().orElse(null);
        if (sessionId == null) {
            return new HttpResponseMessage(httpResponse.sendRedirect("/user/login.html"), httpResponse.getBody());
        }
        SessionStorage.findSessionBy(sessionId).invalidate();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }
}
