package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import model.User;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static db.Database.findUserById;

public class UserLogInController extends AbstractController{

    public static final String INDEX_PATH = "/index.html";
    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";

    public UserLogInController() {
        this.paths = Collections.singletonList("/user/login");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, String> userInfo = httpRequest.getParameters();
        String requestUserId = userInfo.get("userId");
        String requestPassword = userInfo.get("password");

        if(isExistUser(requestUserId, requestPassword)) {
            httpResponse.sendRedirect(HttpStatusCode.SEE_OTHER, INDEX_PATH);
            return;
        }

        httpResponse.sendRedirect(HttpStatusCode.UNAUTHORIZED, LOGIN_FAILED_PATH);
    }

    private boolean isExistUser(String requestUserId, String requestPassword) {
        User user = findUserById(requestUserId);
        return user != null && requestPassword.equals(user.getPassword());
    }

}
