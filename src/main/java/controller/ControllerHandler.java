package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestLine;
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
        if(requestLine.getUri().isEndWithResourceType()) {
            return new ViewController();
        }
        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("컨트롤러를 찾을 수 없습니다."));
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            Controller controller = findController(httpRequest);
            controller.doService(httpRequest, httpResponse);
        } catch (ControllerNotFoundException e) {
            logger.error(e.getMessage());
            httpResponse.response404Header();
        }
    }
}
