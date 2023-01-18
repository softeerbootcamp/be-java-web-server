package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;
import utils.HttpMethod;
import utils.SessionManager;

import java.util.UUID;

import static utils.PathManager.LOGIN_PATH;

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
            return doGet(httpRequest, httpResponse);
        }
        throw new IllegalArgumentException("존재하지 않는 Http 메서드입니다.");
    }

    public HttpResponse doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String cookie = httpRequest.getHeaderValue("Cookie");
        String sid = FileIoUtils.parseSId(cookie);
        try {
            if (SessionManager.getData(UUID.fromString(sid)) != null) {
                httpResponse.setBody(FileIoUtils.writeUserList());
                return httpResponse;
            }
        } catch (NullPointerException e) {
            httpResponse.redirect(LOGIN_PATH);
        }
        return httpResponse;
    }
}
