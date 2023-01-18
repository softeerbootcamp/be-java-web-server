package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.HttpStatus;
import http.response.HttpStatusLine;
import service.StaticFileService;
import util.HttpResponseUtils;

import java.io.IOException;

public class StaticFileController implements Controller {

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {

        return StaticFileService.service(
                httpRequest.getUri(),
                HttpResponseUtils.makeFilePath(httpRequest.getFileNameExtension()),
                httpRequest.getHttpVersion(),
                httpRequest.getContentType());

    }
}
