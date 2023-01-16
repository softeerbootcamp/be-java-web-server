package webserver.controller;

import service.UserService;
import util.HttpParser;
import was.annotation.GetMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.Map;

public class UserController implements Controller{
    @GetMapping("/user/create")
    public HttpResponseMessage create(HttpRequest httpRequest) {
        UserService userService = new UserService();
        userService.addUser(httpRequest.getBody());

        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }
}
