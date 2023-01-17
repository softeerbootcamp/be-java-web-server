package was.controller;

import webserver.domain.HttpRequest;

import java.util.Optional;

public class ControllerFactory {

    public static Controller getControllerInstance(HttpRequest httpRequest) {
        String path = httpRequest.getRequestLine().getUrl();
        if (path.contains("user")) {
            return UserController.getInstance();
        }
        throw new RuntimeException();
    }
}
