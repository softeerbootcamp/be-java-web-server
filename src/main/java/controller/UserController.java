package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;
import utils.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController implements Controller {
    public static final String PATH = "/user/create";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            return doGet(httpRequest, httpResponse);
        }
        if (httpRequest.getHttpMethod().equals(HttpMethod.POST)) {
            return doPost(httpRequest, httpResponse);
        }
        throw new IllegalArgumentException("존재하지 않는 Http 메서드입니다.");
    }

    public HttpResponse doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> query = httpRequest.getQuery();
        userService.join(createUser(query));
        httpResponse.redirectHome();
        return httpResponse;
    }

    public HttpResponse doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = FileIoUtils.parseQueryString(httpRequest.getRequestBody());
        userService.join(createUser(params));
        httpResponse.redirectHome();
        return httpResponse;
    }

    public User createUser(Map<String, String> params) {
        return new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                URLDecoder.decode(params.get("email"), StandardCharsets.UTF_8)
        );
    }
}
