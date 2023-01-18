package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {

    void process(HttpRequest request, HttpResponse httpResponse);
}
