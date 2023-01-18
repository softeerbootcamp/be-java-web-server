package controller;

import http.common.ContentType;
import http.common.URL;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.ResourceUtils;

public class StaticResourceController implements Controller {
    public static final String PATH = "resources";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        URL url = request.getUrl();
        response.setBody(ResourceUtils.loadFileFromClasspath(url.getPath()));

        int idxOfDot = url.getPath().lastIndexOf(".");
        String extension = url.getPath().substring(idxOfDot + 1);
        ContentType contentType = ContentType.fromExtension(extension).orElse(ContentType.TEXT_PLAIN);

        response.addHeader("Content-Type", contentType.getType());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }
}
