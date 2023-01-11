package controller;

import dto.UserInfoDTO;
import io.request.HttpRequest;
import io.request.PathParser;
import io.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final PathParser pathParser = new PathParser();

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        logger.debug("user controller called");
        handleRequest(request, response);
    }

    private void handleRequest(HttpRequest request, HttpResponse response) {
        UserInfoDTO userInfo = new UserInfoDTO(request.getAllQuery());
        userService.signIn(userInfo);
        response.redirect(pathParser.getIndexPageUrl());
    }
}
