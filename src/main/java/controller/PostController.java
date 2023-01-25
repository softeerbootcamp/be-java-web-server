package controller;

import dto.PostCreateDTO;
import filesystem.FileSystem;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AuthService;
import service.PostService;

import java.util.Map;
import java.util.function.BiConsumer;

import static dto.PostCreateDTO.CONTENT;
import static dto.PostCreateDTO.TITLE;
import static filesystem.PathResolver.DOMAIN;
import static filesystem.PathResolver.POST_DETAIL_HTML;

public class PostController implements Controller {

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
        } catch (IllegalArgumentException e) {
            response.redirect(DOMAIN);
        }
    }

    private BiConsumer<HttpRequest, HttpResponse> getHandler(HttpRequest request) {
        return handlers.getOrDefault(handlers.keySet().stream()
                .filter(k -> request.getUrl().matches(k))
                .findAny()
                .orElse(NOT_FOUND), Controller::handleInvalidRequest);
    }

    private void addNewPost(HttpRequest request, HttpResponse response) {
        authService.getUser(request).ifPresent((user) -> {
            PostCreateDTO postInfo = PostCreateDTO.of(user, request.getParameters(TITLE, CONTENT));
            postService.create(postInfo, user);
        });
        response.redirect(DOMAIN);
    }

    private void getPostDetail(HttpRequest request, HttpResponse response) {
        postService.readPost(getPostId(request.getUrl())).ifPresent((post) -> {
            response.update(FileSystem.getPersonalizedResource(POST_DETAIL_HTML,
                    post.getTitle(),
                    post.getCreatedDate(),
                    post.getUser().getName(),
                    post.getContent()
            ));
            response.send();
        });
        response.redirect(DOMAIN);
    }

    private long getPostId(String url) {
        return Long.parseLong(url.split("/", 3)[2]);
    }
}
