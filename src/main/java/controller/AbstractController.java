package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.enums.HttpMethod;

public class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.equals(httpRequest.getHttpMethod())) {
            doGet(httpRequest, httpResponse);
            return;
        }
        if (HttpMethod.POST.equals(httpRequest.getHttpMethod())) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(httpRequest.getHttpMethod());
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpMethodException(httpRequest.getHttpMethod());
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpMethodException(httpRequest.getHttpMethod());
    }
}
