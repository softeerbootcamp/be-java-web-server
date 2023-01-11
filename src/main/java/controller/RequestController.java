package controller;

import http.repsonse.HttpResponse;
import http.request.HttpRequest;

public interface RequestController {
    void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
