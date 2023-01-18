package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.Model;

public interface AuthController extends Controller {

    @Override
    default String execute(HttpRequest request, HttpResponse response, Model model) {
        if (request.getSession() != null) {
            return Controller.super.execute(request, response, model);
        }

        response.redirect("/user/login.html");
        return "";
    }
}
