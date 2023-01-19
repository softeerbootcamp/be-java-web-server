package webserver;

import controller.*;
import db.SessionRepository;
import db.UserRepository;
import service.SessionService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public Map<String, Controller> createController() {
        Map<String, Controller> controllers = new HashMap<>();

        UserRepository userRepository = new UserRepository();
        SessionRepository sessionRepository = new SessionRepository();

        UserService userService = new UserService(userRepository);
        SessionService sessionService = new SessionService(sessionRepository);

        controllers.put("file", new ResourceController());
        controllers.put("/user/create", new UserCreateController(userService));
        controllers.put("/user/login", new UserLogInController(sessionService, userService));
        controllers.put("/", new IndexController(sessionService, userService));
        controllers.put("/user/list.html", new UserListController(sessionService, userService));
        return controllers;
    }
}
