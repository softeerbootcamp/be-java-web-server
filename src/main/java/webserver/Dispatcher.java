package webserver;

import controller.RequestController;
import controller.StaticFileController;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;

public class Dispatcher {
    private final static Map<String, RequestController> controllers;

    static {
        AppConfig appConfig = new AppConfig();
        controllers = Map.of(
                UserController.PATH, new UserController(appConfig.userService()),
                StaticFileController.PATH, new StaticFileController()
        );
    }

    public static void dispatch(HttpRequest request, HttpResponse response) {

        RequestController controller = controllers.get(request.getUri().getPath());
        if (controller == null) {
            controller = controllers.get(StaticFileController.PATH);
        }
        controller.handleRequest(request, response);
    }
}
