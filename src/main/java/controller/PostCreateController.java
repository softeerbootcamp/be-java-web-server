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
import utils.PathManager;
import utils.enums.HttpMethod;

import java.util.Map;

public class PostCreateController implements Controller {
    public static final String PATH = "/post/create";
    private final UserService userService;
    private final SessionService sessionService;
    private final PostService postService;

    public PostCreateController(UserService userService, SessionService sessionService, PostService postService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.postService = postService;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod requestHttpMethod = httpRequest.getHttpMethod();
        if (HttpMethod.POST.equals(requestHttpMethod)) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(requestHttpMethod);
    }

    private void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Map<String, String> bodyParams = httpRequest.getRequestBody();
            String sessionId = httpRequest.getSession();
            sessionService.validateSession(sessionId);
            Session session = sessionService.getSession(httpRequest.getSession()).orElseThrow(NotLogInException::new);
            User user = userService.findUser(session.getUserId());
            postService.createPost(user.getUserId(), bodyParams);
            httpResponse.redirect(PathManager.HOME_PATH);
        }
        catch (NotLogInException e) {
            httpResponse.redirect(PathManager.LOGIN_PATH);
        }
    }
}
