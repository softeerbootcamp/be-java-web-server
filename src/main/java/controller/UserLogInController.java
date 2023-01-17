package controller;

import http.Cookie;
import http.Session;
import http.SessionManager;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import model.User;

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

    public UserLogInController() {
        this.paths = Collections.singletonList("/user/login");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            Map<String, String> userInfo = httpRequest.getParameters();
            String requestUserId = userInfo.get(USER_ID);
            String requestPassword = userInfo.get(PASSWORD);

            validateUser(requestUserId, requestPassword);

            Session session = SessionManager.create();
            session.setAttribute(USER_ID, requestUserId);

            String id = session.getId();
            Cookie cookie = Cookie.of(Session.DEFAULT_SESSION_ID, id + COOKIE_SUFFIX);

            httpResponse.sendRedirect(HttpStatusCode.FOUND, INDEX_PATH, cookie);
        } catch (IllegalArgumentException e) {
            httpResponse.sendRedirect(HttpStatusCode.FOUND, LOGIN_FAILED_PATH);
        }
    }

    private void validateUser(String requestUserId, String requestPassword) {
        User user = findUserById(requestUserId);
        if (user == null || !requestPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

}
