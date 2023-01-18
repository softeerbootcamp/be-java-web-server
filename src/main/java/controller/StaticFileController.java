package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.HttpStatus;
import http.response.HttpStatusLine;
import service.StaticFileService;
import util.HttpResponseUtils;

import java.io.IOException;

public class StaticFileController implements Controller {
    private static final String staticFilePath = "./src/main/resources/static";

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {

        return StaticFileService.service(
                httpRequest.getUri(),
                staticFilePath,
                httpRequest.getHttpVersion(),
                httpRequest.getContentType());

    }
}
