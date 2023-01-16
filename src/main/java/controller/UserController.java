package controller;

import dto.UserInfoDTO;
import http.common.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.util.Map;
import java.util.function.BiConsumer;

import static filesystem.PathParser.DOMAIN;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final Map<Method, BiConsumer<HttpRequest, HttpResponse>> handlers = Map.of(
            Method.GET, this::doGet,
            Method.POST, this::doPost
    );

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("user controller called");
        handlers.get(request.getMethod()).accept(request, response);
    }

    private void doGet(HttpRequest request, HttpResponse response) {
    }

    private void doPost(HttpRequest request, HttpResponse response) {
        UserInfoDTO userInfo = UserInfoDTO.of(request.getParameters("userId", "password", "name", "email"));
        userService.signIn(userInfo);
        response.redirect(DOMAIN);
    }
}
