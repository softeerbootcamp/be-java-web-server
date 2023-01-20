package controller;

import http.common.HttpMethod;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.Model;

public interface Controller {
    default String execute(HttpRequest request, HttpResponse response, Model model) {
        if (request.getMethod() == HttpMethod.GET) {
            return doGet(request, response, model);
        }

        return doPost(request, response, model);
    }
    default String doGet(HttpRequest request, HttpResponse response, Model model) {
        throw new MethodNotAllowException();
    }
    default String doPost(HttpRequest request, HttpResponse response, Model model) {
        throw new MethodNotAllowException();
    }
}
