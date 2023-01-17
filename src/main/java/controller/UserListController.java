package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import utils.HttpMethod;
import utils.SessionManager;
import utils.StatusCode;

import java.util.UUID;

public class UserListController implements Controller {
    public static final String PATH = "/user/list";
    private final UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            String sid = httpRequest.getHeaderValue("sid");
            if (sid == null || SessionManager.getData(UUID.fromString(sid)) == null) {
                httpResponse.redirectLogin();
                return httpResponse;
            }
        }
        httpResponse.setStatusCode(StatusCode.NOTFOUND);
        return httpResponse;
    }
}
