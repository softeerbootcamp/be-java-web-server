package controller;

import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
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

    public static Controller findController(HttpRequest httpRequest) throws IOException, URISyntaxException {
        Controller controller = controllers
                .stream()
                .filter(c -> c.isUri(httpRequest))
                .findFirst()
                .orElse(null);

        logger.debug("Controller {}", controller);
        return controller;
    }
}
