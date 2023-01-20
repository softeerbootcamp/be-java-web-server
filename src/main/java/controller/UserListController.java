package controller;

import exception.FileNotFoundException;
import exception.NonLogInException;
import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            String sessionId = httpRequest.getSessionId();
            sessionService.validateHasSession(sessionId);
            logger.info("Session ID: " + sessionId);

            ContentType contentType = ContentType.from(USER_LIST_PATH);
            String filePath = contentType.getDirectory() + USER_LIST_PATH;

            byte[] body = FileIoUtils.userListToString(
                    userService.getUserList(),
                    filePath,
                    sessionService.getUserName(sessionId)
            );

            httpResponse.forward(HttpStatusCode.OK, contentType, body);

        } catch (NonLogInException e) {
            e.printStackTrace();
            httpResponse.sendRedirect(HttpStatusCode.FOUND, LOGIN_FORM_PATH);

        } catch (FileNotFoundException e) {
            byte[] body = FileIoUtils.load404ErrorFile();
            httpResponse.do404(body);
            throw new RuntimeException(e);
        }
    }

}
