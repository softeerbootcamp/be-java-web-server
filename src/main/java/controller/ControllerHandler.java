package controller;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ControllerHandler{
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
    }
    public static Controller findController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        if(!uri.isQueryParameterExist() && uri.isEndWithResourceType()) {
            return new ViewController();
        }
        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("Not Found Controller"));
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            Controller controller = findController(httpRequest);
            controller.doService(httpRequest, httpResponse);
        } catch (ControllerNotFoundException |ResourceTypeNotFoundException e) {
            logger.error(e.getMessage());
            httpResponse.response404Header();
            httpResponse.responseBody(e.getMessage().getBytes());
        }
    }
}
