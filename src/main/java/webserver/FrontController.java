package webserver;

import Controller.Controller;
import exception.ControllerNotFoundException;
import exception.UrlNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllerMapper;

import java.io.DataOutputStream;

public class FrontController { // TODO 클래스명 변경 또는 메서드명 변경

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse, DataOutputStream dos) {
        try {
            Controller controller = ControllerMapper.getController(httpRequest);
            controller.process(httpRequest, httpResponse);

            httpResponse.send(dos);
        } catch (ControllerNotFoundException | UrlNotFoundException e) {
            logger.error(e.getMessage());

            httpResponse.notFound(httpRequest);
            httpResponse.send(dos);
        }
    }
}
