package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.Post;
import service.PostService;
import view.Model;

import java.util.List;

public class IndexController implements Controller {
    public static final String PATH = "/index.html";

    public final PostService postService;

    public IndexController() {
        this.postService = new PostService();
    }

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        List<Post> posts = postService.getPosts();
        model.addAttribute("data", posts);
        return "/index.html";
    }
}
