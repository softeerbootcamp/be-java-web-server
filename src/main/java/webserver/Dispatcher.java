package webserver;

import controller.Controller;
import controller.LoginController;
import controller.StaticFileController;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Dispatcher {
    private final static Map<String, Controller> controllers;
    private final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    static {
        AppConfig appConfig = new AppConfig();
        controllers = Map.of(
                UserController.PATH, new UserController(appConfig.userService()),
                StaticFileController.PATH, new StaticFileController(),
                LoginController.PATH, new LoginController(appConfig.userService())
        );
    }

    public static HttpResponse dispatch(HttpRequest request, HttpResponse response) {

        Controller controller = controllers.get(request.getUri().getPath());
        if (controller == null) {
            controller = controllers.get(StaticFileController.PATH);
        }
        return controller.service(request, response);
    }
}
