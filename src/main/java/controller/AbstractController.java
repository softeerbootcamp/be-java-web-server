package controller;

import http.Uri;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AbstractController implements Controller {

    protected List<String> paths;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        HttpMethod httpMethod = httpRequest.getMethod();
        if (httpMethod.equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }

}
