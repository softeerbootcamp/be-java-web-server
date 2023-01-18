package webserver.controller;

import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static model.response.HttpStatusCode.NOT_FOUND;

public class UserHandlerAdapter implements WasHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserHandlerAdapter.class);

    private final Map<String, UserController> controllerMap = new HashMap<>();

    public UserHandlerAdapter() {
        controllerMap.put("/user/create", new UserCreateController());
        controllerMap.put("/user/login", new UserLoginController());
    }

    public Response process(Request request) {
        String url = request.getUrl();
        logger.debug("user process start url : {}", url);
        UserController userController = controllerMap.get(url);
        if (userController == null) {
            logger.error("404 NOT FOUND");
            return Response.of(request.getHttpVersion(), NOT_FOUND, Map.of(), new byte[0]);
        }
        return userController.service(request);
    }
}
