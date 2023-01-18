package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import utils.ContentType;
import utils.FileIoUtils;
import utils.HttpMethod;
import utils.StatusCode;

public class StaticFileController implements Controller {
    public final static String PATH = "/";
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new IllegalArgumentException("유효하지 않은 HTTP 메서드입니다");
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.setBody(FileIoUtils.loadFile(httpRequest.getUri().getPath()));
            httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(httpRequest.getUri().getPath())));
            httpResponse.setStatusCode(StatusCode.OK);
        }
        catch (IllegalArgumentException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
    }
}
