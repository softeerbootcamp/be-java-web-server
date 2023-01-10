package controller;

import io.request.HttpRequest;
import io.response.Response;

public interface Controller {
    void handle(HttpRequest request, Response response);
}
