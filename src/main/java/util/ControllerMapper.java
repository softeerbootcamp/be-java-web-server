package util;

import Controller.Controller;
import Controller.UserController;
import Controller.ResourceController;
import http.request.HttpRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerMapper {

    private static final Map<String, Controller> controllerMap = new LinkedHashMap<>();

    static {
        controllerMap.put("/user", new UserController());
    }

    public static Controller getController(HttpRequest request) {
        String url = request.getUrl();

        for (String key : controllerMap.keySet()) {
            if (url.startsWith(key)) {
                return controllerMap.get(key);
            }
        }

        return new ResourceController();
    }
}
