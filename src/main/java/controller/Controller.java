package controller;

import http.common.HttpMethod;
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
    void doGet(HttpRequest request, HttpResponse response);
    void doPost(HttpRequest request, HttpResponse response);
}
