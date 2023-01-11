package Controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import util.HttpResponseUtils;

import java.io.IOException;

public class StaticFileController implements Controller{

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        String contentType = httpRequest.getContentType();
        String filePath = HttpResponseUtils.makeFilePath(contentType);
        byte[] responseBody = HttpResponseUtils.makeBody(httpRequest, filePath);
        return new HttpResponse(HttpStatus.OK, responseBody, contentType);
    }
}
