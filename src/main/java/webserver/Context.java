package webserver;

import controller.Controller;
import controller.ResourceController;
import controller.UserCreateController;
import controller.UserLogInController;
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
        return controllers;
    }
}
