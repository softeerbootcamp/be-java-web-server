package controller;

import exception.ControllerNotFoundException;
import exception.ResourceTypeNotFoundException;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    private static final Map<String, Controller> controllers = new HashMap<>() {{
        put("user", new UserController());
    }};

    public static HttpResponse handle(HttpRequest httpRequest) throws Exception {
        try {
            Controller controller = findController(httpRequest.getUri());
            return controller.doService(httpRequest);
        } catch (ResourceTypeNotFoundException | ControllerNotFoundException e) {
            logger.error(e.getMessage());
            return HttpResponseFactory.NOT_FOUND(e.getMessage());
        }
    }

    public static Controller findController(HttpUri httpUri) {
        return controllers.getOrDefault(httpUri.getDetachControllerPath(), ResourceController.getInstance());
    }
}
