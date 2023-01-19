package webserver.controller;

import exception.SessionExpiredException;
import exception.SessionNotFoundException;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.UserService;

import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserLogoutController implements UserController{
    private static final Logger logger = LoggerFactory.getLogger(UserLogoutController.class);
    private final UserService userService = new UserService();
    @Override
    public Response service(Request request) {
        try {
            return userService.logoutUser(request);
        } catch (SessionNotFoundException | SessionExpiredException e) {
            logger.error(e.getMessage());
            return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/index.html"), new byte[0]);
        }
    }
}
