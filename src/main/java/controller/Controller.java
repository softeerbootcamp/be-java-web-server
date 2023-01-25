package controller;

import exception.HttpNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    static String NOT_FOUND = "NOT_FOUND";

    static void handleInvalidRequest(HttpRequest request, HttpResponse response) {
        throw new HttpNotFoundException();
    }

    void service(HttpRequest request, HttpResponse response);
}
