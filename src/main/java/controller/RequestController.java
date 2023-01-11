package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;

public interface RequestController {
    void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
