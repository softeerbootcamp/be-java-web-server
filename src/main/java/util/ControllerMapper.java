package util;

import Controller.Controller;
import Controller.DynamicResourceController;
import Controller.StaticResourceController;
import Controller.UserController;
import exception.ControllerNotFoundException;
import http.request.HttpRequest;
import service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerMapper {
    private static final Map<String, Controller> controllerMap = new LinkedHashMap<>();

    static {
        controllerMap.put(UserController.PREFIX, new UserController(new UserService()));
    }

    public static Controller getController(HttpRequest request) {
        String url = request.getUrl();
        if (url.equals("/") || url.endsWith(".html")) {
            return DynamicResourceController.getInstance();
        }
        if (StaticResourceController.isSupported(url)) {
            return StaticResourceController.getInstance();
        }

        for (String key : controllerMap.keySet()) {
            if (url.startsWith(key)) {
                return controllerMap.get(key);
            }
        }

        throw new ControllerNotFoundException("요청에 해당되는 컨트롤러가 없습니다.");
    }
}