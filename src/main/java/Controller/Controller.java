package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {

    String process(HttpRequest request, HttpResponse httpResponse);
}
