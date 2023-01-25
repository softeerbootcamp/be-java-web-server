package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.user.UserService;
import utils.enums.HttpMethod;
import utils.SessionManager;

import javax.naming.AuthenticationException;
import java.util.Map;

import static utils.PathManager.HOME_PATH;
import static utils.PathManager.LOGIN_FAILED_PATH;

public class UserLoginController implements Controller {
    public static final String PATH = "/user/login";
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
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

    private void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyParams = httpRequest.getRequestBody();
        try {
            userService.login(bodyParams.get("userId"), bodyParams.get("password"));
            httpResponse.setCookie(SessionManager.createSession(userService.findUser(bodyParams.get("userId"))));
            httpResponse.redirect(HOME_PATH);
        } catch (AuthenticationException e) {
            httpResponse.redirect(LOGIN_FAILED_PATH);
        }
    }
}
