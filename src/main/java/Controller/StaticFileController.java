package Controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

import java.io.IOException;

public class StaticFileController implements Controller{

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        String contentType = httpRequest.getContentType();
        String filePath = HttpResponse.makeFilePath(contentType);
        byte[] responseBody = HttpResponse.makeBody(httpRequest, filePath);
        return new HttpResponse(HttpStatus.OK, responseBody, contentType);
    }
}
