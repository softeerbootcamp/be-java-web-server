package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.ContentType;
import utils.FileIoUtils;
import utils.HttpMethod;
import utils.StatusCode;


public class StaticFileController implements Controller {
    public final static String PATH = "/";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!HttpMethod.GET.equals(httpRequest.getHttpMethod())) {
            throw new IllegalArgumentException("Invalid HTTP Method");
        }
        doGet(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getUri().getPath();
        try {
            httpResponse.setBody(FileIoUtils.loadFile(path));
            httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(path)));
        } catch (NullPointerException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
    }
}
