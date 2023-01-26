package controller;

import exception.NotLogInException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import model.User;
import service.post.PostService;
import service.session.SessionService;
import service.user.UserService;
import utils.PathManager;

import java.util.Map;

public class PostCreateController extends AbstractController {
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
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Map<String, String> bodyParams = httpRequest.getRequestBody();
            String sessionId = httpRequest.getSession();
            sessionService.validateSession(sessionId);
            Session session = sessionService.getSession(httpRequest.getSession());
            User user = userService.findUser(session.getUserId());
            postService.createPost(user.getUserId(), bodyParams);
            httpResponse.redirect(PathManager.HOME_PATH);
        } catch (NotLogInException e) {
            httpResponse.redirect(PathManager.LOGIN_PATH);
        }
    }
}
