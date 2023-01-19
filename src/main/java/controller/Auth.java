package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Auth {
    default boolean auth(HttpRequest request, HttpResponse response) {
        if (request.getSession() == null) {
            response.redirect("/user/login.html");
            return false;
        }
        return true;
    }
}
