package webserver;

import controller.RequestController;
import controller.StaticFileController;
import controller.UserController;
import db.MemoryDatabase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserServiceImpl;

import java.util.Map;

public class Dispatcher {
    private final static Map<String, RequestController> controllers;
    private final static String STATIC_FILE_CONTROLLER = "StaticFileController";

    static {
        controllers = Map.of(
                UserController.PATH, new UserController(new UserServiceImpl(new MemoryDatabase())),
                STATIC_FILE_CONTROLLER, new StaticFileController()
        );
    }

    public static void dispatch(HttpRequest request, HttpResponse response) {

        RequestController controller = controllers.get(request.getUri().getPath());
        if (controller == null) {
            controller = controllers.get(STATIC_FILE_CONTROLLER);
        }
        controller.handleRequest(request, response);
    }
}
