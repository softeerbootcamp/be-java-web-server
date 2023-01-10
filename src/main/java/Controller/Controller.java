package Controller;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    void makeResponse(HttpRequest httpRequest, HttpResponse httpResponse);
}
