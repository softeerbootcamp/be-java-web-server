package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ViewController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        File file = FileIoUtil.getFile(httpRequest.getUri());
        byte[] body = Files.readAllBytes(file.toPath());
        return HttpResponseFactory.OK(httpRequest.getContentType(), body);
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
