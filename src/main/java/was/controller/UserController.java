package was.controller;

import model.User;
import service.UserService;
import was.annotation.PostMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.Map;

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
    @PostMapping("/user/create")
    public HttpResponseMessage create(HttpRequest httpRequest) {
        userService.addUser(httpRequest.getBody());

        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }

    @PostMapping("/user/login")
    public HttpResponseMessage login(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getBody();
        User user = new User(body.get("userId"), body.get("password"));

        HttpResponse httpResponse = new HttpResponse();
        if (userService.login(user)) {
            return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
        }
        return new HttpResponseMessage(httpResponse.unauthorized(), httpResponse.getBody());
    }
}
