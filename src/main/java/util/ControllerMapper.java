package util;

import controller.Controller;
import controller.ViewController;
import controller.NotFoundController;
import controller.UserController;
import db.Database;
import model.request.Request;
import model.request.RequestLine;
import service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerMapper {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMapper.class);

    private static final Map<String, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put("user", new UserController(new UserService(new Database())));
    }

    public static Controller selectController(Request request) {
        RequestLine requestLine = request.getRequestLine();
        logger.debug("Request Line: {}", requestLine.getUri());

        if(Objects.nonNull(requestLine.getContentType())) return new ViewController();

        for(Map.Entry<String, Controller> controllerEntry : controllerMap.entrySet()) {
            if(isExistController(controllerEntry.getKey(), requestLine.getControllerCriteria())) {
                return controllerEntry.getValue();
            }
        }

        return new NotFoundController();
    }

    private static boolean isExistController(String controller, String controllerCriteria) {
        return controller.equals(controllerCriteria);
    }
}
