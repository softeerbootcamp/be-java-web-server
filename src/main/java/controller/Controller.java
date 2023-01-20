package controller;

import exception.HttpNotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);

    static void handleInvalidRequest(HttpRequest request, HttpResponse response) {
        throw new HttpNotFoundException();
    }
}
