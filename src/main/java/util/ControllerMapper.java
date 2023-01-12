package util;

import Controller.Controller;
import Controller.UserController;
import Controller.ResourceController;
import http.request.HttpRequest;

public class ControllerMapper {

    public static Controller getController(HttpRequest request) {
        String url = request.getUrl();

        if (url.startsWith("/user")) {
            return new UserController();
        }

        return new ResourceController();
    }
}
