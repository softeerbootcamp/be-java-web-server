package controller;

import dto.LogInDTO;
import dto.SignUpDTO;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.util.Map;
import java.util.function.BiConsumer;

import static dto.SignUpDTO.*;
import static filesystem.PathResolver.DOMAIN;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> handlers = Map.of(
            "/user/create", this::signUp,
            "/user/login", this::logIn
    );

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("user controller called");
        handlers.get(request.getUrl()).accept(request, response);
    }

    private void logIn(HttpRequest request, HttpResponse response) {
        LogInDTO userInfo = LogInDTO.of(request.getParameters(USER_ID, PASSWORD));
        userService.logIn(userInfo);
        // response.setCookie();
        response.redirect(DOMAIN);
    }

    private void signUp(HttpRequest request, HttpResponse response) {
        SignUpDTO userInfo = SignUpDTO.of(request.getParameters(USER_ID, PASSWORD, NAME, EMAIL));
        userService.signUp(userInfo);
        response.redirect(DOMAIN);
    }
}
