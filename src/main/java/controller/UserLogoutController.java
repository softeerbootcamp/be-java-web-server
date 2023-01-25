package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.session.SessionService;
import utils.PathManager;
import utils.enums.HttpMethod;

public class UserLogoutController implements Controller {
    public static final String PATH = "/user/logout";
    private final SessionService sessionService;

    public UserLogoutController(SessionService sessionService) {
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

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        sessionService.removeSession(httpRequest.getSession());
        httpResponse.redirect(PathManager.LOGIN_PATH);
    }
}
