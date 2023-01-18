package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;
import utils.HttpMethod;
import utils.SessionManager;
import utils.StatusCode;

import java.util.UUID;

public class UserListController implements Controller {
    public static final String PATH = "/user/list";
    private static Logger logger = LoggerFactory.getLogger(UserListController.class);
    private final UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            String cookie = httpRequest.getHeaderValue("Cookie");
            if (cookie == null) {
                httpResponse.redirectLogin();
                return httpResponse;
            }
            String sid = cookie.split("=")[1].trim();
            if (SessionManager.getData(UUID.fromString(sid)) != null) {
                httpResponse.setBody(FileIoUtils.writeUserList());
                return httpResponse;
            }
            httpResponse.redirectLogin();
            return httpResponse;
        }
        httpResponse.setStatusCode(StatusCode.NOTFOUND);
        return httpResponse;
    }
}
