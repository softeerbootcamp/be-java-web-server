package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void handle(HttpRequest request, HttpResponse response);
}
