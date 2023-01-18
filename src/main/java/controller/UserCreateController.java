package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.HttpMethod;

import java.util.Map;

import static utils.PathManager.HOME_PATH;

public class UserCreateController implements Controller {
    public static final String PATH = "/user/create";
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
    private final UserService userService;


    public UserCreateController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 Http 메서드입니다.");
    }

    private void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyParams = httpRequest.getRequestBody();
        userService.createUser(bodyParams);
        httpResponse.redirect(HOME_PATH);
    }
}
