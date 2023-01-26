package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

@FunctionalInterface
public interface Handler {

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
