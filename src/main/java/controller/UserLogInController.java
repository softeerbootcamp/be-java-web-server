package controller;

import http.cookie.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;

import java.io.IOException;
import java.util.Map;

public class UserLogInController extends AbstractController {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String COOKIE_SUFFIX = "; path=/";
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
            Map<String, String> userInfo = httpRequest.getParameters();
            String requestUserId = userInfo.get(USER_ID);
            String requestPassword = userInfo.get(PASSWORD);

            logger.info("id: " + requestUserId);
            logger.info("id: " + requestPassword);

            userService.validateUser(requestUserId, requestPassword);

            String sessionId = sessionService.makeSessionId();
            sessionService.makeSession(sessionId, "login");

            logger.info("Login Success");

            httpResponse.sendRedirect(
                    INDEX_PATH,
                    Cookie.of(DEFAULT_SESSION_ID, sessionId + COOKIE_SUFFIX)
            );

        } catch (IllegalArgumentException e) {
            logger.info("Login Failed");
            httpResponse.sendRedirect(LOGIN_FAILED_PATH);
        }
    }

}
