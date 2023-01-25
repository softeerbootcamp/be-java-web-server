package webserver;

import controller.*;
import http.exception.NotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.Model;
import view.ViewHandler;

import java.util.Map;
import java.util.Objects;

public class Dispatcher {
    private final static Map<String, Controller> controllers;

    static {
        controllers = Map.of(
                IndexController.PATH, new IndexController(),
                StaticResourceController.PATH, new StaticResourceController(),
                SignUpController.PATH, new SignUpController(),
                LoginController.PATH, new LoginController(),
                UserListController.PATH, new UserListController(),
                PostController.PATH, new PostController()
        );
    }

    public static void handle(HttpRequest request, HttpResponse response) {
        Controller controller = controllers.get(request.getUrl().getPath());
        Model model = new Model();
        if (Objects.nonNull(controller)) {
            String path = controller.execute(request, response, model);
            ViewHandler.handle(path, request, response, model);
            return;
        }

        Controller staticResourcesController = controllers.get(StaticResourceController.PATH);
        String path = staticResourcesController.execute(request, response, model);
        ViewHandler.handle(path, request, response, model);

        if (response.getBody().length == 0) {
            throw new NotFoundException("페이지를 찾을 수 없습니다.");
        }
    }
}
