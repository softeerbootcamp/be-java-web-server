package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestLine;
import http.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

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
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        if (!uri.isQueryParameterExist() && uri.isEndWithResourceType()) {
            return new ViewController().doService(httpRequest);
        }
        return controllers
                .stream()
                .filter(controller -> controller.isMatch(httpRequest))
                .findFirst()
                .orElseThrow(() -> new ControllerNotFoundException("Not Found Controller"))
                .doService(httpRequest);
    }
}
