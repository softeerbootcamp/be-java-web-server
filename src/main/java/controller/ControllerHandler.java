package controller;

import exception.ControllerNotFoundException;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ControllerHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
    }

    public static HttpResponse handle(HttpRequest httpRequest) throws IOException, URISyntaxException {
        Controller controller = findController(httpRequest);
        return controller.doService(httpRequest);
    }

    public static Controller findController(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        if (!httpUri.isQueryParameterExist() && httpUri.isEndWithResourceType()) {
            return new ViewController();
        }
        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("Not Found Controller"));
    }
}
