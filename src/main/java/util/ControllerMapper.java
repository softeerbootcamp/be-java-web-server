package util;

import controller.Controller;
import controller.HomeController;

import controller.NotFoundController;
import controller.UserController;
import model.request.RequestLine;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    private static final Map<String, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put("/", new HomeController());
        controllerMap.put("index.html", new HomeController());
        controllerMap.put("user", new UserController());
    }

    public static Controller selectController(RequestLine requestLine) {
        for(Map.Entry<String, Controller> controllerEntry : controllerMap.entrySet()) {
            if(controllerEntry.getKey().equals(requestLine.getControllerCriteria())) {
                return controllerEntry.getValue();
            }
        }

        return new NotFoundController();
    }
}
