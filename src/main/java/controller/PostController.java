package controller;

import dto.PostCreateDTO;
import filesystem.FileSystem;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Post;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AuthService;
import service.exception.EmptyInputException;
import service.exception.NotFoundException;
import service.post.PostService;

import java.util.Map;
import java.util.function.BiConsumer;

import static dto.PostCreateDTO.CONTENT;
import static dto.PostCreateDTO.TITLE;
import static filesystem.PathResolver.DOMAIN;
import static filesystem.PathResolver.POST_DETAIL_HTML;

public class PostController implements Controller {

    private static final Map<Class, String> redirectUrls = Map.of(
            EmptyInputException.class, DOMAIN,
            NotFoundException.class, DOMAIN
    );
    private final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService = new PostService();
    private final AuthService authService = new AuthService();
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> handlers = Map.of(
            "/post/create", this::addNewPost,
            "/post/(\\d)+", this::getPostDetail
    );

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("post controller called");
        try {
            getHandler(request).accept(request, response);
        } catch (RuntimeException e) {
            response.redirect(redirectUrls.get(e.getClass()));
        }
    }

    private BiConsumer<HttpRequest, HttpResponse> getHandler(HttpRequest request) {
        return handlers.getOrDefault(handlers.keySet().stream()
                .filter(k -> request.getUrl().matches(k))
                .findAny()
                .orElse(NOT_FOUND), Controller::handleInvalidRequest);
    }

    private void addNewPost(HttpRequest request, HttpResponse response) {
        User user = authService.getUser(request);
        postService.addNewPost(PostCreateDTO.of(user, request.getParameters(TITLE, CONTENT)), user);
        response.redirect(DOMAIN);
    }

    private void getPostDetail(HttpRequest request, HttpResponse response) {
        Post post = postService.readPost(getPostId(request.getUrl()));
        response.update(FileSystem.getPersonalizedResource(POST_DETAIL_HTML,
                post.getTitle(),
                post.getCreatedDate(),
                post.getUser().getName(),
                post.getContent()
        ));
        response.send();
    }

    private long getPostId(String url) {
        return Long.parseLong(url.split("/", 3)[2]);
    }
}
