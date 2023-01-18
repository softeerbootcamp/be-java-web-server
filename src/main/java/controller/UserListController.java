package controller;

import db.Database;
import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.Model;

public class UserListController implements AuthController {
    public static final String PATH = "/user/list";

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        model.addAttribute("authUser", request.getSession().user());
        model.addAttribute("users", Database.findAll());
        response.addHeader("Content-Type", ContentType.TEXT_HTML.getType());
        return "/user/list.html";
    }
}
