package webserver;

import controller.*;
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
                UserCreateController.PATH, new UserCreateController(appConfig.userService()),
                StaticFileController.PATH, new StaticFileController(),
                UserLoginController.PATH, new UserLoginController(appConfig.userService()),
                UserListController.PATH, new UserListController(appConfig.userService())
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
