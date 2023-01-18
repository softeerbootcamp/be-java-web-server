package controller;

import http.request.HttpRequest;
import http.request.HttpSession;
import http.response.HttpResponse;

import java.util.Optional;

import static http.common.HttpSessionStorage.getSession;

public interface AuthController extends Controller {

    @Override
    default void execute(HttpRequest request, HttpResponse response) {
        String sessionId = request.getHeaders().getCookie("session");
        Optional<HttpSession> sessionOptional = getSession(sessionId);

        if (sessionOptional.isPresent()) {
            request.setSession(sessionOptional.get());
            Controller.super.execute(request, response);
            return;
        }

        response.redirect("/user/login.html");
    }
}
