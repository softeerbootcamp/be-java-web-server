package webserver.controller;

import model.request.Request;
import model.response.Response;
import webserver.service.UserService;

public interface UserController {
    UserService userService = new UserService();
    Response service(Request request);
}
