package servlet;

import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Servlet {
    HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException;

    void get(HttpRequest httpRequest);

    HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
