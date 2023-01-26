package controller;

import exception.HttpMethodException;
import exception.NotLogInException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import model.User;
import service.post.PostService;
import service.session.SessionService;
import service.user.UserService;
import utils.FileUtils;
import utils.PathManager;
import utils.enums.ContentType;
import utils.enums.HttpMethod;
import utils.enums.StatusCode;

import java.io.IOException;

public class HtmlFileController implements Controller {
    public static final String PATH = "html";
    private final UserService userService;
    private final SessionService sessionService;
    private final PostService postService;

    public HtmlFileController(UserService userService, SessionService sessionService, PostService postService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.postService = postService;
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
        String sessionId = httpRequest.getSession();
        try {
            sessionService.validateSession(sessionId);
            Session session = sessionService.getSession(httpRequest.getSession()).orElse(null);
            User user = userService.findUser(session.getUserId());
            if (path.equals(PathManager.HOME_PATH)) {
                httpResponse.setBody(FileUtils.createPostListPage(user, postService.findAll()));
            }
            httpResponse.setBody(FileUtils.createPage(path, user));
        } catch (NotLogInException e) {
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
