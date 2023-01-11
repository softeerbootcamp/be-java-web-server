package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Handler {

    String handle(HttpRequest request, HttpResponse httpResponse);
}
