package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        String url = httpRequest.getUrl();
        File file = FileIoUtil.getFile(httpRequest.getUri());
        byte[] body = Files.readAllBytes(file.toPath());

        return HttpResponseFactory.OK(httpRequest.getContentType(), body);
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
