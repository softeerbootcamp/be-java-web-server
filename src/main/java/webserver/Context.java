package webserver;

import controller.*;
import db.BoardRepository;
import db.SessionRepository;
import db.UserRepository;
import service.BoardService;
import service.SessionService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public Map<String, Controller> createController() {
        Map<String, Controller> controllers = new HashMap<>();

        UserRepository userRepository = new UserRepository();
        SessionRepository sessionRepository = new SessionRepository();
        BoardRepository boardRepository = new BoardRepository();

        UserService userService = new UserService(userRepository);
        SessionService sessionService = new SessionService(sessionRepository);
        BoardService boardService = new BoardService(boardRepository);

        controllers.put("file", new ResourceController(sessionService));
        controllers.put("/user/create", new UserCreateController(userService));
        controllers.put("/user/login", new UserLogInController(sessionService, userService));
        controllers.put("/index.html", new IndexController(sessionService));
        controllers.put("/user/list.html", new UserListController(sessionService, userService));
        controllers.put("/board/create", new BoardCreateController(sessionService, boardService));
        return controllers;
    }
}
