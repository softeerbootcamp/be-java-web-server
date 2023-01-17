package was.controller;

import service.UserService;
import was.annotation.PostMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

public class UserController implements Controller{
    private static UserController userController;
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
        UserService userService = UserService.getInstance();
        userService.addUser(httpRequest.getBody());

        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }
}
