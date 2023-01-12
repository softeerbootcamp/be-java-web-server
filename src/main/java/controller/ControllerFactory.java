package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControllerFactory {

    private static final Logger logger = LoggerFactory.getLogger(ControllerFactory.class);

    private static final List<Controller> controllers;

    private ControllerFactory() {
    }

    static {
        controllers = new ArrayList<>();
        controllers.add(new IndexController());
        controllers.add(new ResourceController());
        controllers.add(new UserCreateController());
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = controllers
                .stream()
                .filter(c -> c.isUri(httpRequest))
                .findFirst()
                .orElse(null);

        if(controller == null) {
            httpResponse.do404();
            return;
        }

        logger.debug("Controller {}",controller);
        controller.service(httpRequest, httpResponse);
    }


}
