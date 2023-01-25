package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.PostService;
import view.Model;

public class PostController implements Controller, Auth {
    public static final String PATH = "/qna";

    private final PostService postService;

    public PostController() {
        this.postService = new PostService();
    }

    @Override
    public String doPost(HttpRequest request, HttpResponse response, Model model) {
        if (!auth(request, response)) {
            return "";
        }
        postService.create(
                request.getData().get("writer"),
                request.getData().get("title"),
                request.getData().get("content")
        );
        response.redirect("/index.html");
        return "";
    }
}
