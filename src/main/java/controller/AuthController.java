package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import static http.common.HttpSessionStorage.existSession;

public interface AuthController extends Controller {

    @Override
    default void execute(HttpRequest request, HttpResponse response) {
        String sessionId = request.getHeaders().getCookie("session");

        if (existSession(sessionId)) {
            Controller.super.execute(request, response);
            return;
        }

        response.redirect("/user/login.html");
    }
}
