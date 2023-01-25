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
                UserLogoutController.PATH, new UserLogoutController(),
                StaticFileController.PATH, new StaticFileController(),
                UserLoginController.PATH, new UserLoginController(appConfig.userService()),
                UserListController.PATH, new UserListController(appConfig.userService()),
                HtmlFileController.PATH, new HtmlFileController(appConfig.userService())
        );
    }

    public static void dispatch(HttpRequest request, HttpResponse response) {
        if (request.hasHtmlRequest()) {
            controllers.get(HtmlFileController.PATH).service(request, response);
            return;
        }
        Controller controller = controllers.getOrDefault(request.getUri().getPath()
                , controllers.get(StaticFileController.PATH));
        controller.service(request, response);
    }
}
