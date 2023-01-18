package controller;

import http.cookie.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import service.SessionService;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static db.Database.findUserById;

public class UserLogInController extends AbstractController {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String COOKIE_SUFFIX = "; path=/";
    public static final String INDEX_PATH = "/index.html";
    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";

    private final SessionService sessionService;

    public UserLogInController(SessionService sessionService) {
        this.paths = Collections.singletonList("/user/login");
        this.sessionService = sessionService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserLogInController.class);

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            Map<String, String> userInfo = httpRequest.getParameters();
            String requestUserId = userInfo.get(USER_ID);
            String requestPassword = userInfo.get(PASSWORD);

            logger.info("id: " + requestUserId);
            logger.info("id: " + requestPassword);

            validateUser(requestUserId, requestPassword);

            Session session = new Session();
            sessionService.addSession(session);

            Cookie cookie = Cookie.of(Session.DEFAULT_SESSION_ID, session.getId() + COOKIE_SUFFIX);

            session.setAttribute(cookie.toString(), "login");
            sessionService.addSession(session);

            logger.info("Login Success");

            httpResponse.sendRedirect(INDEX_PATH, cookie);

        } catch (IllegalArgumentException e) {
            logger.info("Login Failed");
            httpResponse.sendRedirect(LOGIN_FAILED_PATH);
        }
    }

    private void validateUser(String requestUserId, String requestPassword) {
        User user = findUserById(requestUserId);
        if (user == null || !requestPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

}
