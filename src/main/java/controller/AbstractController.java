package controller;

import exception.FileNotFoundException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, FileNotFoundException {
        HttpMethod httpMethod = httpRequest.getMethod();
        if (httpMethod.equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, FileNotFoundException {
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }

}
