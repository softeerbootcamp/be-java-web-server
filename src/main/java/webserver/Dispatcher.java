package webserver;

import controller.Controller;
import controller.LoginController;
import controller.SignUpController;
import controller.StaticResourceController;
import http.exception.NotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;
import java.util.Objects;

public class Dispatcher {
    private final static Map<String, Controller> controllers;

    static {
        controllers = Map.of(
                StaticResourceController.PATH, new StaticResourceController(),
                SignUpController.PATH, new SignUpController(),
                LoginController.PATH, new LoginController()
        );
    }

    public static void handle(HttpRequest request, HttpResponse response) {
        Controller controller = controllers.get(request.getUrl().getPath());
        if (Objects.nonNull(controller)) {
            controller.execute(request, response);
            return;
        }

        Controller staticResourcesController = controllers.get(StaticResourceController.PATH);
        staticResourcesController.execute(request, response);

        if (response.getBody().length == 0) {
            throw new NotFoundException("페이지를 찾을 수 없습니다.");
        }
    }
}
