package controller;

import dto.PostCreateDTO;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AuthService;
import service.PostService;

import java.util.Map;
import java.util.function.BiConsumer;

import static dto.PostCreateDTO.*;
import static filesystem.PathResolver.DOMAIN;

public class PostController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService = new PostService();
    private final AuthService authService = new AuthService();
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> handlers = Map.of(
            "/post/create", this::addNewPost
    );

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("post controller called");
        try {
            handlers.getOrDefault(request.getUrl(), Controller::handleInvalidRequest)
                    .accept(request, response);
        } catch (IllegalArgumentException e) {
            response.redirect(DOMAIN);
        }
    }

    private void addNewPost(HttpRequest request, HttpResponse response) {
        authService.getUser(request).ifPresent((user) -> {
            PostCreateDTO postInfo = PostCreateDTO.of(user, request.getParameters(TITLE, CONTENT));
            postService.create(postInfo, user);
        });
        response.redirect(DOMAIN);
    }
}
