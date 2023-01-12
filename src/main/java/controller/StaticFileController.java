package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import utils.ContentType;
import utils.FileIoUtils;
import utils.StatusCode;

public class StaticFileController implements Controller {
    public final static String PATH = "/";
    @Override
    public HttpResponse service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFile(httpRequest.getUri().getPath());
            httpResponse.setBody(body);
            httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(httpRequest.getUri().getPath())));
            httpResponse.setStatusCode(StatusCode.OK);
        }
        catch (IllegalArgumentException e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
        return httpResponse;
    }
}
