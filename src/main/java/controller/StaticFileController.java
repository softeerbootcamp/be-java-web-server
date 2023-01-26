package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileUtils;
import utils.enums.ContentType;
import utils.enums.HttpMethod;
import utils.enums.StatusCode;

import java.io.IOException;


public class StaticFileController extends AbstractController {
    public final static String PATH = "/";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod requestHttpMethod = httpRequest.getHttpMethod();
        if (HttpMethod.GET.equals(requestHttpMethod)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(requestHttpMethod);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getUri().getPath();
        try {
            httpResponse.setBody(FileUtils.loadFile(path));
            httpResponse.setContentType(ContentType.getContentType(FileUtils.getExtension(path)));
        } catch (IOException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
    }
}
