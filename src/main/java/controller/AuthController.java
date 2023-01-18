package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface AuthController extends Controller {

    @Override
    default void execute(HttpRequest request, HttpResponse response) {
        if (request.getSession() != null) {
            Controller.super.execute(request, response);
            return;
        }

        response.redirect("/user/login.html");
    }
}
