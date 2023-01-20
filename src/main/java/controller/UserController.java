package controller;

import dto.LogInDTO;
import dto.SignUpDTO;
import http.common.Cookie;
import http.common.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.util.Map;
import java.util.function.BiConsumer;

import static dto.SignUpDTO.*;
import static filesystem.PathResolver.DOMAIN;
import static filesystem.PathResolver.LOGIN_FAILED_HTML;
import static http.common.Session.SESSION_FIELD_NAME;

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
        handlers.getOrDefault(request.getUrl(), Controller::handleInvalidRequest)
                .accept(request, response);
    }

    private void logIn(HttpRequest request, HttpResponse response) {
        LogInDTO userInfo = LogInDTO.of(request.getParameters(USER_ID, PASSWORD));
        Session session = userService.logIn(userInfo);
        if (session != null) {
            response.setCookie(new Cookie(SESSION_FIELD_NAME, session.getId()));
            response.redirect(DOMAIN);
            return;
        }
        response.redirect(LOGIN_FAILED_HTML);
    }

    private void signUp(HttpRequest request, HttpResponse response) {
        SignUpDTO userInfo = SignUpDTO.of(request.getParameters(USER_ID, PASSWORD, NAME, EMAIL));
        userService.signUp(userInfo);
        response.redirect(DOMAIN);
    }
}
