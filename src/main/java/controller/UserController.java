package controller;

import dto.LoginDTO;
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
        try {
            handlers.getOrDefault(request.getUrl(), Controller::handleInvalidRequest)
                    .accept(request, response);
        } catch () {

        } catch () {
            response.redirect(LOGIN_FAILED_HTML);
        }
    }

    private void logIn(HttpRequest request, HttpResponse response) {
        Session session = userService.logIn(LoginDTO.of(request.getParameters(LOGIN_ID, PASSWORD)));
            response.setCookie(Cookie.of(SESSION_FIELD_NAME, session.getId()));
            response.redirect(DOMAIN);
    }

    private void signUp(HttpRequest request, HttpResponse response) {
        try {
            SignUpDTO userInfo = SignUpDTO.of(request.getParameters(LOGIN_ID, PASSWORD, NAME, EMAIL));
            userService.signUp(userInfo);
        } catch (IllegalStateException e) {
        } finally {
            response.redirect(DOMAIN);
        }
    }
}
