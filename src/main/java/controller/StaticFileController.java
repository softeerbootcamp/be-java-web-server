package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.*;


public class StaticFileController implements Controller {
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

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getUri().getPath();
        try {
            httpResponse.setBody(FileIoUtils.loadFile(path));
            httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(path)));
        } catch (IllegalArgumentException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
            httpResponse.setBody(FileIoUtils.loadFile(PathManager.ERROR_PATH));
        }
    }
}
