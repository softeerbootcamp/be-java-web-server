package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class UserListController implements AuthController {
    public static final String PATH = "/user/list";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.redirect("/user/list.html");
    }
}
