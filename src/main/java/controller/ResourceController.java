package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.request.Uri;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResourceController implements Controller {
    private final List<String> paths;
    public ResourceController() {
        this.paths = Arrays.asList(".html", ".ico", ".css", ".js", "woff", "ttf", "png");
    }
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        doGet(httpRequest, httpResponse);
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setResponse(httpRequest.getUri().getPath());
    }

}
