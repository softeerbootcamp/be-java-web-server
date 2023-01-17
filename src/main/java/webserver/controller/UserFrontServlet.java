package webserver.controller;

import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserFrontServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserFrontServlet.class);

    private final Map<String, UserController> controllerMap = new HashMap<>();

    public UserFrontServlet() {
        controllerMap.put("/user/create", new UserCreateController());
    }

    public void process(Request request, Response response) {
        String url = request.getUrl();
        logger.debug("process start url : {}", url);
        UserController userController = controllerMap.get(url);
        if (userController == null) {
            //TODO 404 응답
            return;
        }
        userController.service(request, response);
    }
}
