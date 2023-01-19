package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserCreateController extends AbstractController{
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = UserService.from(httpRequest);

        User user = userService.postJoinService();

        Database.addUser(user);
        return HttpResponse.found("/index.html").build();
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        UserService userService = UserService.from(httpRequest);
        User user = userService.postJoinService();
        Database.addUser(user);

        return HttpResponse.found("/index.html").build();
    }
}
