package webserver.controller;

import exception.UserNotFoundException;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.UserService;

import java.sql.SQLException;
import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserLoginController implements UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    private final UserService userService = new UserService();
    @Override
    public Response service(Request request) {
        try {
            return userService.loginUser(request);
        } catch (UserNotFoundException | SQLException e) {
            logger.debug("로그인 실패!!");
            return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/user/login_failed.html"), new byte[0]);
        }
    }
}
