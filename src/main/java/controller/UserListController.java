package controller;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;

import java.io.IOException;

public class UserListController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String LOGIN_FORM_PATH = "/user/login.html";
    public static final String USER_LIST_PATH = "/user/list.html";


    private final SessionService sessionService;
    private final UserService userService;

    public UserListController(SessionService sessionService, UserService userService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            sessionService.validateHasSession(httpRequest.getSessionId());

            ContentType contentType = ContentType.from(USER_LIST_PATH);
            String filePath = contentType.getDirectory() + USER_LIST_PATH;

            byte[] body = userService.makeUserListBody(filePath);

            httpResponse.forward(HttpStatusCode.OK, contentType, body);

        } catch (NullPointerException e) {
            httpResponse.sendRedirect(LOGIN_FORM_PATH);
        }
    }

}
