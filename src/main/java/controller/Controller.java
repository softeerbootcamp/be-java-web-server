package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    HttpResponse doService(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    boolean isMatch(HttpRequest httpRequest);
}
