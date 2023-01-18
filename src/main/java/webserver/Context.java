package webserver;

import controller.*;
import service.SessionService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public Map<String, Controller> createController() {
        Map<String, Controller> controllers = new HashMap<>();
        UserService userService = UserService.getInstance();
        SessionService sessionService = SessionService.getInstance();

        controllers.put("file", new ResourceController());
        controllers.put("/user/create", new UserCreateController(userService));
        controllers.put("/user/login", new UserLogInController(sessionService));
        controllers.put("/", new IndexController());
        return controllers;
    }
}
