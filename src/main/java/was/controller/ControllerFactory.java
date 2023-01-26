package was.controller;

import service.UserService;
import webserver.domain.HttpRequest;

import java.util.Optional;

public class ControllerFactory {

    public static Controller getControllerInstance(HttpRequest httpRequest) {
        String path = httpRequest.getRequestLine().getUrl();
        if (path.startsWith("/user")) {
            return UserController.getInstance();
        }
        if (path.startsWith("/boards")) {
            return BoardController.getInstance();
        }
        throw new RuntimeException();
    }
}
