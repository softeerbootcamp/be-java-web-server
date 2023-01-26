package webserver;

import controller.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerMapper {
    private final static Map<String, Controller> controllers;
    private final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    static {
        AppConfig appConfig = new AppConfig();
        controllers = Map.of(
                UserCreateController.PATH, new UserCreateController(appConfig.userService()),
                UserLogoutController.PATH, new UserLogoutController(appConfig.sessionService()),
                StaticFileController.PATH, new StaticFileController(),
                UserLoginController.PATH, new UserLoginController(appConfig.userService(), appConfig.sessionService()),
                UserListController.PATH, new UserListController(appConfig.userService(), appConfig.sessionService()),
                HtmlFileController.PATH, new HtmlFileController(appConfig.userService(), appConfig.sessionService(), appConfig.postService()),
                PostCreateController.PATH, new PostCreateController(appConfig.userService(), appConfig.sessionService(), appConfig.postService())
        );
    }

    public static Controller mapController(HttpRequest request) {
        if (request.getUri().getPath().contains("html")) {
            return controllers.get(HtmlFileController.PATH);
        }
        return controllers.getOrDefault(request.getUri().getPath(), controllers.get(StaticFileController.PATH));
    }
}
