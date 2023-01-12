package controller;

import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class DefaultController implements Controller {

    public void execute(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
