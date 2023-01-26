package controller;

import exception.LogInFailedException;
import exception.UserNotFoundException;
import http.cookie.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;

import http.response.HttpStatusCode;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;

import java.io.IOException;

public class UserLogInController extends AbstractController {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String INDEX_PATH = "/index.html";
    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    public static final String DEFAULT_SESSION_ID = "JSESSIONID";
    private static final Logger logger = LoggerFactory.getLogger(UserLogInController.class);

    private final SessionService sessionService;
    private final UserService userService;

    public UserLogInController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            String id = httpRequest.getParameter(USER_ID);
            String pw = httpRequest.getParameter(PASSWORD);
            userService.validateUser(id, pw);

            Session session = sessionService.makeSession(id, userService.getUserName(id));

            logger.info("Login Success");

            httpResponse.sendRedirect(
                    HttpStatusCode.FOUND,
                    INDEX_PATH,
                    Cookie.of(DEFAULT_SESSION_ID, session.getSessionId())
            );

        } catch (LogInFailedException | UserNotFoundException e) {
            logger.info("Login Failed");
            e.printStackTrace();
            httpResponse.sendRedirect(HttpStatusCode.FOUND, LOGIN_FAILED_PATH);
        }
    }
}
