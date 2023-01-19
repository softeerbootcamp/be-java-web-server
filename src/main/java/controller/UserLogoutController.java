package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.PathManager;
import utils.SessionManager;

import java.util.UUID;

public class UserLogoutController implements Controller {
    public static final String PATH = "/user/logout";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        doGet(httpRequest, httpResponse);
    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        SessionManager.removeSession(UUID.fromString(httpRequest.getSession()));
        httpResponse.redirect(PathManager.LOGIN_PATH);
    }
}
