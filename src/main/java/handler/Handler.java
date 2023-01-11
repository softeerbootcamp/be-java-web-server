package handler;

import http.request.HttpRequest;

public interface Handler {

    String handle(HttpRequest request);
}
