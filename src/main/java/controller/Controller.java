package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
