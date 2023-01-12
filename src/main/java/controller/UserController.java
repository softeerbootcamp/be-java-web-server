package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import model.User;
import service.UserService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController implements RequestController {
    private final UserService userService;
    public static final String PATH = "/user/create";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> querys = httpRequest.getQuerys();
        User user = new User(
                querys.get("userId"),
                querys.get("password"),
                querys.get("name"),
                URLDecoder.decode(querys.get("email"), StandardCharsets.UTF_8)
        );
        userService.join(user);
        httpResponse.redirect();
    }
}
