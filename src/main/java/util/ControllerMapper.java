package util;

import controller.Controller;
import controller.ViewController;
import controller.NotFoundController;
import controller.UserController;
import db.Database;
import model.request.Request;
import service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerMapper {
    private static final Map<String, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put("user", new UserController(new UserService(new Database())));
    }

    public static Controller selectController(Request request) {
        if(Objects.nonNull(request.getRequestLine().getContentType())) return new ViewController();

        for(Map.Entry<String, Controller> controllerEntry : controllerMap.entrySet()) {
            if(controllerEntry.getKey().equals(request.getRequestLine().getControllerCriteria())) {
                return controllerEntry.getValue();
            }
        }

        return new NotFoundController();
    }
}
