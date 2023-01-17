package webserver.controller;

import model.request.Request;
import model.response.Response;
import webserver.service.UserService;

public interface UserController {
    UserService userService = new UserService();
    void service(Request request, Response response);
}
