package controller;

import http.common.HttpMethod;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    default void execute(HttpRequest request, HttpResponse response) {
        if (request.getMethod() == HttpMethod.GET) {
            doGet(request, response);
            return;
        }

        doPost(request, response);
    }
    default void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }
    default void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }
}
