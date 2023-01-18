package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
