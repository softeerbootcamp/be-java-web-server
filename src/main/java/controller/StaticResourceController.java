package controller;

import http.common.HttpBody;
import http.common.MediaType;
import http.common.URI;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.ResourceUtils;

public class StaticResourceController implements Controller {
    public static final String PATH = "resources";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        URI uri = request.getUri();
        HttpBody body = new HttpBody(
                ResourceUtils.loadFileFromClasspath(uri.getPath())
        );
        response.setBody(body);

        int idxOfDot = uri.getPath().lastIndexOf(".");
        String extension = uri.getPath().substring(idxOfDot + 1);
        MediaType mediaType = MediaType.fromExtension(extension).orElse(MediaType.TEXT_PLAIN);

        response.addHeader("Content-Type", mediaType.getType());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }
}
