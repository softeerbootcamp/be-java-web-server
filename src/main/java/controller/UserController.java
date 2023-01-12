package controller;

import dto.UserInfoDTO;
import io.request.HttpRequest;
import io.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import static io.request.PathParser.DOMAIN;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        logger.debug("user controller called");
        handleRequest(request, response);
    }

    private void handleRequest(HttpRequest request, HttpResponse response) {
        UserInfoDTO userInfo = UserInfoDTO.of(request.getParameters("userId", "password", "name", "email"));
        userService.signIn(userInfo);
        response.redirect(DOMAIN);
    }
}
