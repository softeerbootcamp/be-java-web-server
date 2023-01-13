package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static utils.FileIoUtils.load404ErrorFile;


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

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        Controller controller = controllers
                .stream()
                .filter(c -> c.isUri(httpRequest))
                .findFirst()
                .orElse(null);

        if(controller == null) {
            byte[] errorBody = load404ErrorFile();
            httpResponse.do404(errorBody);
            return;
        }

        logger.debug("Controller {}",controller);
        controller.service(httpRequest, httpResponse);
    }


}
