package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest, HttpResponse httpResponse);
}
