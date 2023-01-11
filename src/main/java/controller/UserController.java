package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import service.UserService;

public class UserController implements RequestController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        
    }
}
