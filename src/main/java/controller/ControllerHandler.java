package controller;

import exception.ControllerNotFoundException;
import exception.ResourceTypeNotFoundException;
import http.SessionHandler;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ControllerHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
    }

    public static HttpResponse handle(HttpRequest httpRequest) throws Exception {
        try {
            Controller controller = findController(httpRequest);
            return controller.doService(httpRequest);
        } catch (ResourceTypeNotFoundException | ControllerNotFoundException e) {
            logger.error(e.getMessage());
            return HttpResponseFactory.NOT_FOUND(e.getMessage());
        }
    }

    public static Controller findController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        if (httpUri.isEndWithResourceType() && !httpUri.isEndWithHtml()) {
            return new ViewController();
        }
        if (httpUri.isEndWithHtml()) {
            return new DynamicController();
        }

        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("Not Found Controller"));
    }
}
