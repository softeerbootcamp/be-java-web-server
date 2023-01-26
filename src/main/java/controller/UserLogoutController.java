package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.session.SessionService;
import utils.PathManager;

public class UserLogoutController extends AbstractController {
    public static final String PATH = "/user/logout";
    private final SessionService sessionService;

    public UserLogoutController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        sessionService.removeSession(httpRequest.getSession());
        httpResponse.redirect(PathManager.LOGIN_PATH);
    }
}
