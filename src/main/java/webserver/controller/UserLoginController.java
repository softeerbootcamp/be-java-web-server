package webserver.controller;

import model.request.Request;
import model.response.Response;
import webserver.service.UserService;

public class UserLoginController implements UserController {

    private final UserService userService = new UserService();

    @Override
    public Response service(Request request) {
        return userService.loginUser(request);
    }
}
