package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

abstract class AbstractController implements Controller {
    @Override
    public abstract void service(HttpRequest httpRequest, HttpResponse httpResponse);

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
