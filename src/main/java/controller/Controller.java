package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    HttpResponse makeResponse(HttpRequest httpRequest);
}
