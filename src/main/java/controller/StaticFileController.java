package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import utils.ContentType;
import utils.FileIoUtils;
import utils.StatusCode;

public class StaticFileController implements RequestController{
    public final static String STATIC_FILE_CONTROLLER = "StaticFileController";
    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFile(httpRequest.getUri().getPath());
            httpResponse.setBody(body);
            httpResponse.setContentType(ContentType.getContentType(httpRequest.getExtension()));
            httpResponse.setStatusCode(StatusCode.OK);
        }
        catch (IllegalArgumentException e) {
            if (httpRequest.getExtension().equals("/")) {
                httpResponse.redirect();
                return;
            }
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
    }
}
