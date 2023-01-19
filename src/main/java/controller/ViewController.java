package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.response.HttpStatus;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class ViewController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        HttpUri httpUri = requestLine.getHttpUri();
        File file = FileIoUtil.getFile(requestLine.getHttpUri());
        byte[] body = Files.readAllBytes(file.toPath());

        if (httpUri.getPath().endsWith(".html") && SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            body = DynamicResolver.showUserName(file, httpSession.getUserName());
        }

        return HttpResponseFactory.OK(requestHeader.getContentType(), body);
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
