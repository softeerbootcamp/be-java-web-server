package webserver.controller;

import exception.UserNotFoundException;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.UserService;

public class UserLoginController implements UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    private final UserService userService = new UserService();
    @Override
    public void service(Request request, Response response) {
        try {
            userService.loginUser(request, response);
        } catch (UserNotFoundException e) {
            logger.debug("로그인 실패!!");
        }

    }
}
