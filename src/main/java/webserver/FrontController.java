package webserver;

import Controller.Controller;
import exception.ControllerNotFoundException;
import exception.UrlNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllerMapper;
import view.ViewResolver;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class FrontController {

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    public void process(HttpRequest httpRequest, HttpResponse httpResponse, OutputStream out) {
        try {
            Controller controller = ControllerMapper.getController(httpRequest);

            String viewName = controller.process(httpRequest, httpResponse);
            String viewPath = ViewResolver.resolveViewName(viewName);

            sendResponse(out, httpResponse, viewPath);
        } catch (ControllerNotFoundException | UrlNotFoundException | FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse, String viewPath) throws IOException {
        httpResponse.makeBodyMessage(viewPath);

        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.send(dos);
    }
}
