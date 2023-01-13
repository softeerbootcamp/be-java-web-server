package util;

import Controller.Controller;
import Controller.UserController;
import Controller.ResourceController;
import http.request.HttpRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ControllerMapper {

    private static final List<String> staticFileExtension = List.of(".css", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png", ".js", ".ico");
    private static final Map<String, Controller> controllerMap = new LinkedHashMap<>();

    static {
        controllerMap.put(UserController.PREFIX, new UserController());
    }

    public static Controller getController(HttpRequest request) {
        String url = request.getUrl();

        for (String key : controllerMap.keySet()) {
            if (url.startsWith(key)) {
                return controllerMap.get(key);
            }
        }

        if (url.equals("/") || staticFileExtension.stream().anyMatch(url::endsWith)) {
            return new ResourceController();
        }

        throw new IllegalArgumentException();
    }
}
