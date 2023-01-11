package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.Uri;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResourceController implements Controller {
    private final List<String> paths;
    public ResourceController() {
        this.paths = Arrays.asList(".html", ".ico", ".css", ".js", "woff", "ttf");
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
