package controller;

import io.request.HttpRequest;
import io.response.HttpResponse;

public interface Controller {
    void handle(HttpRequest request, HttpResponse response);
}
