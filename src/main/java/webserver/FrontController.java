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

public class FrontController { // TODO 클래스명 변경 또는 메서드명 변경

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    public void process(HttpRequest httpRequest, HttpResponse httpResponse, DataOutputStream dos) {
        try {
            Controller controller = ControllerMapper.getController(httpRequest);

            String viewName = controller.process(httpRequest, httpResponse);
            String viewPath = ViewResolver.resolveViewName(viewName);

            sendResponse(dos, httpResponse, viewPath);
        } catch (ControllerNotFoundException | UrlNotFoundException | FileNotFoundException e) {
            logger.error(e.getMessage());

            httpResponse.notFound(httpRequest);
            httpResponse.send(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, HttpResponse httpResponse, String viewPath) throws IOException {
        httpResponse.makeBodyMessageWithFile(viewPath);

        httpResponse.send(dos);
    }
}
