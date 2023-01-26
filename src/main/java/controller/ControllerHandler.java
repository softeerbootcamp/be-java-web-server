package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerHandler {

    private static final Map<String, Controller> controllers = new HashMap<>() {{
        put("user", new UserController());
        put("memo", new MemoController());
    }};

    public static HttpResponse handle(HttpRequest httpRequest) {
        Controller controller = findController(httpRequest.getUri());
        return controller.doService(httpRequest);
    }

    public static Controller findController(HttpUri httpUri) {
        return controllers.getOrDefault(httpUri.getDetachControllerPath(), ResourceController.getInstance());
    }
}
