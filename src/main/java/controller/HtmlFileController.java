package controller;

import exception.HttpMethodException;
import exception.NotLogInException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Post;
import model.Session;
import model.User;
import service.post.PostService;
import service.session.SessionService;
import service.user.UserService;
import utils.FileUtils;
import utils.enums.ContentType;
import utils.enums.HttpMethod;
import utils.enums.StatusCode;

import java.util.Collection;

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
        if (!FileUtils.fileExists(path)) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
            return;
        }
        if (path.equals("/index.html")) {
            setIndexHtml(httpRequest, httpResponse);
            return;
        }
        setOtherHtml(httpRequest, httpResponse);
    }

    private void setIndexHtml(HttpRequest httpRequest, HttpResponse httpResponse) {
        Collection<Post> posts = postService.findAll();
        try {
            String sessionId = httpRequest.getSession();
            sessionService.validateSession(sessionId);
            Session session = sessionService.getSession(sessionId);
            User user = userService.findUser(session.getUserId());
            httpResponse.setBody(FileUtils.createPostListPage(user, posts));
        } catch (NotLogInException e) {
            httpResponse.setBody(FileUtils.createPostListPage(null, posts));
        } finally {
            httpResponse.setContentType(ContentType.HTML);
        }
    }

    private void setOtherHtml(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getUri().getPath();
        try {
            String sessionId = httpRequest.getSession();
            sessionService.validateSession(sessionId);
            Session session = sessionService.getSession(sessionId);
            User user = userService.findUser(session.getUserId());
            httpResponse.setBody(FileUtils.createPage(path, user));
        } catch (NotLogInException e) {
            httpResponse.setBody(FileUtils.createPage(path, null));
        } finally {
            httpResponse.setContentType(ContentType.HTML);
        }
    }
}
