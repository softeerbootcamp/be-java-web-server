package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.session.SessionService;
import service.user.UserService;
import utils.enums.HttpMethod;

import javax.naming.AuthenticationException;
import java.util.Map;

import static utils.PathManager.HOME_PATH;
import static utils.PathManager.LOGIN_FAILED_PATH;

public class UserLoginController extends AbstractController {
    public static final String PATH = "/user/login";
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private final UserService userService;
    private final SessionService sessionService;

    public UserLoginController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod requestHttpMethod = httpRequest.getHttpMethod();
        if (HttpMethod.POST.equals(requestHttpMethod)) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(requestHttpMethod);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyParams = httpRequest.getRequestBody();
        try {
            userService.login(bodyParams.get("userId"), bodyParams.get("password"));
            httpResponse.setCookie(sessionService.createSession(userService.findUser(bodyParams.get("userId"))));
            httpResponse.redirect(HOME_PATH);
        } catch (AuthenticationException e) {
            httpResponse.redirect(LOGIN_FAILED_PATH);
        }
    }
}
