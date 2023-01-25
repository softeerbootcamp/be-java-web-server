package controller;

import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.HttpMethod;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PostService;
import service.SessionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PostController implements RequestController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private static PostController postController;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/qna/form", (req) -> createPost(req));
        put("/qna/show", (req) -> showPost(req));
    }};


    private PostController() {
    }

    public static PostController get() {
        if (postController == null)
            postController = new PostController();
        return postController;
    }

    @Override
    public CustomHttpResponse handleRequest(CustomHttpRequest req) {
        for (String path : routingTable.keySet()) {
            if (req.getUrl().startsWith(path)) {
                return routingTable.get(path).handleRequest(req);
            }
        }
        return CustomHttpFactory.NOT_FOUND();
    }


    public CustomHttpResponse createPost(CustomHttpRequest req) {
        Set<HttpMethod> allowed = Set.of(HttpMethod.GET, HttpMethod.POST);
        if (!allowed.contains(req.getHttpMethod())) {
            return CustomHttpFactory.METHOD_NOT_ALLOWED();
        }

        if (req.getHttpMethod() == HttpMethod.GET) {
            if(SessionService.isValidSSID(req.getSSID()))
                return StaticFileController.get().handleRequest(req);
            return CustomHttpFactory.REDIRECT("/user/login");
        }

        User user = SessionService.getUserBySessionId(req.getSSID()).orElse(null);
        if (user == null)
            return CustomHttpFactory.REDIRECT("/user/login");

        PostService.createPost(req.parseBodyFromUrlEncoded());

        return CustomHttpFactory.REDIRECT("/qna/show");
    }

    public CustomHttpResponse showPost(CustomHttpRequest req){
        Set<HttpMethod> allowed = Set.of(HttpMethod.GET);
        if (!allowed.contains(req.getHttpMethod())) {
            return CustomHttpFactory.METHOD_NOT_ALLOWED();
        }
        return StaticFileController.get().handleRequest(req);
    }
}
