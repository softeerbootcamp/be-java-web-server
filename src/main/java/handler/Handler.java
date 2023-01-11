package handler;

import http.HttpRequest;

public interface Handler {

    String handle(HttpRequest request);
}
