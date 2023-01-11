package webserver;

import controller.RequestController;
import controller.UserController;
import db.MemoryDatabase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserServiceImpl;

import java.util.Map;

public class Dispatcher {
    private final static Map<String, RequestController> controllers;

    static {
        controllers = Map.of(
                UserController.PATH, new UserController(new UserServiceImpl(new MemoryDatabase()))
        );
    }

    public static void handle(HttpRequest request, HttpResponse response) {

        RequestController controller = controllers.get(request.getUri().getPath());
    }
}
