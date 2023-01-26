package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import service.session.SessionService;
import service.user.UserService;
import utils.FileUtils;
import utils.enums.ContentType;
import utils.enums.HttpMethod;
import utils.enums.StatusCode;

import java.io.IOException;

public class HtmlFileController implements Controller {
    public static final String PATH = "html";
    private final UserService userService;
    private final SessionService sessionService;

    public HtmlFileController(UserService userService, SessionService sessionService) {
        this.userService = userService;
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
        String path = httpRequest.getUri().getPath();
        try {
            Session session = sessionService.getSession(httpRequest.getSession()).orElse(null);
            httpResponse.setBody(FileUtils.createPage(path, userService.findUser(session.getUserId())));
        } catch (NullPointerException e) {
            try {
                httpResponse.setBody(FileUtils.createPage(path, null));
            } catch (IOException err) {
                httpResponse.setStatusCode(StatusCode.NOTFOUND);
                return;
            }
        } catch (IOException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
            return;
        }
        httpResponse.setContentType(ContentType.HTML);
    }
}
