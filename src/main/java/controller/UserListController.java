package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
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
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 Http 메서드입니다.");
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sid = httpRequest.getSession();
        try {
            Session session = SessionManager.getData(UUID.fromString(sid));
            if (session != null) {
                httpResponse.setBody(FileIoUtils.makeUserListPage(userService.findAllUsers(), session));
            }
            if (session == null)
                httpResponse.redirect(LOGIN_PATH);
        } catch (NullPointerException e) {
            httpResponse.redirect(LOGIN_PATH);
        }
    }
}
