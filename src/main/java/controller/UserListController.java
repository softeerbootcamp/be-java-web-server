package controller;

import exception.HttpMethodException;
import exception.NotLogInException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.session.SessionService;
import service.user.UserService;
import utils.FileUtils;
import utils.enums.HttpMethod;

import java.io.IOException;

import static utils.PathManager.LOGIN_PATH;

public class UserListController extends AbstractController {
    public static final String PATH = "/user/list";
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private final UserService userService;
    private final SessionService sessionService;

    public UserListController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod requestHttpMethod = httpRequest.getHttpMethod();
        if (HttpMethod.GET.equals(requestHttpMethod)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(requestHttpMethod);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sid = httpRequest.getSession();
        try {
            sessionService.validateSession(sid);
            Session session = sessionService.getSession(sid);
            httpResponse.setBody(FileUtils.createUserListPage(userService.findAllUsers(), userService.findUser(session.getUserId())));
        } catch (NullPointerException | NotLogInException | IOException e) {
            httpResponse.redirect(LOGIN_PATH);
        }
    }
}
