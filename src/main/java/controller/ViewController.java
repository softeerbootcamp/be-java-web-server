package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.*;
import http.response.HttpResponse;
import http.response.HttpStatus;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ViewController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        HttpUri httpUri = requestLine.getHttpUri();
        File file = FileIoUtil.getFile(requestLine.getHttpUri());
        byte[] body = Files.readAllBytes(file.toPath());

        if(httpUri.getPath().endsWith(".html") && SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            String fileData = new String(body);
            fileData = fileData.replace("로그인", httpSession.getUserName());
            body = fileData.getBytes();
        }

        return HttpResponse.of(
                HttpStatus.OK,
                requestHeader.getContentType(),
                new HashMap<>(),
                body,
                requestLine.getVersion());
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
